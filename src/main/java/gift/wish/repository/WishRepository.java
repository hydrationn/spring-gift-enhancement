package gift.wish.repository;

import gift.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findAllByMemberId(Long memberId);
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}

