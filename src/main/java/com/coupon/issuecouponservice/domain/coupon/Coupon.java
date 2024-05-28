package com.coupon.issuecouponservice.domain.coupon;

import com.coupon.issuecouponservice.domain.common.Timestamped;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends Timestamped {

    @Version
    private Long version;

    @Id
    @Tsid
    private Long id;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "coupon_image")
    private String couponImage;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "remain_quantity", nullable = false)
    private int remainQuantity;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status")
    private StockStatus stockStatus;

    @Column(name = "expired_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE)
    private List<UserCoupon> userCoupons = new ArrayList<>();


    @Builder
    public Coupon(String couponName, String couponImage, int totalQuantity, LocalDateTime expiredAt) {
        this.couponName = couponName;
        this.couponImage = couponImage;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = totalQuantity; // 생성 시점에서 초기 잔여 수량은 전체 수량이다.
        this.stockStatus = StockStatus.IN_STOCK;
        this.expiredAt = expiredAt;
    }

    /* == 생성 메서드 == */
    public static Coupon CreateCoupon(CouponCreationParam param) {
        return Coupon.builder()
                .couponName(param.getCouponName())
                .couponImage(param.getCouponImage())
                .totalQuantity(param.getTotalQuantity())
                .expiredAt(param.getExpiredAt())
                .build();
    }

    /* == 수정 메서드 == */
    public void modifyCoupon(CouponModificationParam param) {
        if (param.getCouponName() != null && !param.getCouponName().isBlank() && !this.couponName.equals(param.getCouponName())) {
            this.couponName = param.getCouponName();
        }

        if (this.totalQuantity != param.getTotalQuantity()) {
            this.totalQuantity = param.getTotalQuantity();
        }

        if (this.remainQuantity != param.getRemainQuantity()) {
            this.remainQuantity = param.getRemainQuantity();
        }

        if (param.getExpiredAt() != null && !this.expiredAt.equals(param.getExpiredAt())) {
            this.expiredAt = param.getExpiredAt();
        }
    }

    /* == 삭제 메서드 == */
    public void deleteCoupon() {
        this.isDeleted = true;
    }

    /* == 검증 메서드 == */
    public void validateCoupon() {
        if(this.stockStatus.equals(StockStatus.OUT_OF_STOCK)) throw new IllegalArgumentException("쿠폰이 매진되었습니다.");
        if(this.expiredAt.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
    }

    /* == 검증 메서드 : 중복 검증  == */
    private boolean couponAlreadyIssue(Long couponId) {
        for(UserCoupon userCoupon : userCoupons){
            if(userCoupon.getCoupon().getId().equals(couponId)){
                return true;
            }
        }
        return false;
    }

    /* == 쿠폰 재고 감소 == */
    public void decreaseQuantity() {
        this.remainQuantity = this.remainQuantity - 1;
        if(this.remainQuantity <= 0){
            this.stockStatus = StockStatus.OUT_OF_STOCK;
        }

    }
}
