package gift.wish.service;

import gift.product.entity.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
import gift.security.exception.AccessDeniedException;
import gift.wish.dto.WishResponseDto;
import gift.wish.entity.Wish;
import gift.wish.exception.DuplicateWishException;
import gift.wish.exception.WishNotFoundException;
import gift.wish.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishServiceImpl implements WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishServiceImpl(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Override
    public WishResponseDto createWish(Long memberId, Long productId) {
        if (wishRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new DuplicateWishException(memberId, productId);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Wish savedWish = wishRepository.save(new Wish(memberId, productId));

        return WishResponseDto.of(savedWish, product);
    }

    @Override
    public Page<WishResponseDto> findAllWishesByMemberId(Long memberId, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findAllByMemberId(memberId, pageable);

        return wishes.map(wish -> {
                    Product product = productRepository.findById(wish.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(wish.getProductId()));
                    return WishResponseDto.of(wish, product);
                });
    }

    @Override
    @Transactional
    public void deleteWish(Long memberId, Long wishId) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new WishNotFoundException(wishId));

        if (!wish.isOwner(memberId)) {
            throw new AccessDeniedException(memberId);
        }

        wishRepository.delete(wish);
    }
}
