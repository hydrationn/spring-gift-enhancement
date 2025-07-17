package gift.wish.entity;

import jakarta.persistence.*;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    private Long memberId;

    @JoinColumn(name = "product_id", nullable = false)
    private Long productId;

    protected Wish() {}

    public Wish(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public boolean isOwner(Long memberId) {
        return this.memberId.equals(memberId);
    }
}
