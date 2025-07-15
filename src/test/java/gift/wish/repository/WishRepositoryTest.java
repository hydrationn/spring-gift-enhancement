package gift.wish.repository;

import gift.wish.entity.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
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
        wishes.save(new Wish(1L, 2L));
        wishes.save(new Wish(1L, 3L));
        wishes.save(new Wish(2L, 1L));

        // when
        List<Wish> result = wishes.findAllByMemberId(1L);

        // then
        assertThat(result).hasSize(2)
                .extracting(Wish::getProductId)
                .containsExactlyInAnyOrder(2L, 3L);
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
