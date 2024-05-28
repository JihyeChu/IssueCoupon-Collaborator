package com.coupon.issuecouponservice.domain.coupon;

import com.coupon.issuecouponservice.domain.user.User;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_coupon", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"coupon_id", "user_id"})
})
public class UserCoupon {

    @Id
    @Tsid
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public UserCoupon(Coupon coupon, User user){
        this.coupon = coupon;
        this.user = user;
    }

    public static UserCoupon CreateUserCoupon(Coupon coupon, User user) {
        coupon.validateCoupon();
        coupon.decreaseQuantity();
        return UserCoupon.builder()
                .coupon(coupon)
                .user(user)
                .build();
    }
}
