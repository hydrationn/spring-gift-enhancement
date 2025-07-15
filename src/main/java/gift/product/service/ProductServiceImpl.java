package gift.product.service;

import gift.common.pagination.PageRequestDto;
import gift.common.pagination.PageResult;
import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.entity.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(
                null,
                productRequestDto.name(),
                productRequestDto.price(),
                productRequestDto.imageUrl()
        );
        Product created = productRepository.save(product);
        return ProductResponseDto.from(created);
    }

    @Override
    public PageResult<ProductResponseDto> findAllProducts(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto.page(), pageRequestDto.size());

        Page<Product> page = productRepository.findAll(pageable);

        List<ProductResponseDto> content = page.getContent().stream()
                .map(ProductResponseDto::from)
                .toList();

        return new PageResult<>(
                content,
                page.getNumber(),
                page.getTotalPages(),
                page.getSize(),
                (int) page.getTotalElements()
        );
    }


    @Override
    public ProductResponseDto findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductResponseDto.from(product);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.update(productRequestDto.name(), productRequestDto.price(), productRequestDto.imageUrl());

        return ProductResponseDto.from(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
