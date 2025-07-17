package gift.wish.repository;

import gift.wish.entity.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishes;

    @Test
    void save() {
        // given
        Wish toSave = new Wish(1L, 2L);

        // when
        Wish saved = wishes.save(toSave);

        // then
        assertAll(
                () -> assertThat(saved.getId()).isNotNull(),
                () -> assertThat(saved.getMemberId()).isEqualTo(1L),
                () -> assertThat(saved.getProductId()).isEqualTo(2L)
        );
    }

    @Test
    void findById() {
        // given
        Wish saved = wishes.save(new Wish(1L, 2L));

        // when
        Optional<Wish> result = wishes.findById(saved.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getMemberId()).isEqualTo(1L);
        assertThat(result.get().getProductId()).isEqualTo(2L);
    }

    @Test
    void findAllByMemberId() {
        // given
        for (long i = 1; i <= 20; i++) {
            wishes.save(new Wish(1L, i));
        }

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Wish> result = wishes.findAllByMemberId(1L, pageable);

        // then
        assertThat(result.getContent()).hasSize(10);
        assertThat(result.getTotalElements()).isEqualTo(20);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);
    }

    @Test
    void existsByMemberIdAndProductId() {
        // given
        wishes.save(new Wish(1L, 2L));

        // then
        assertThat(wishes.existsByMemberIdAndProductId(1L, 2L)).isTrue();
        assertThat(wishes.existsByMemberIdAndProductId(1L, 3L)).isFalse();
    }

    @Test
    void delete() {
        // given
        Wish saved = wishes.save(new Wish(1L, 2L));

        // when
        wishes.delete(saved);

        // then
        assertThat(wishes.findById(saved.getId())).isEmpty();
    }
}
