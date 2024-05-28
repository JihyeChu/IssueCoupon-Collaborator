package com.coupon.issuecouponservice.dto.response.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponOneForm {

    private Long couponId;
    private String couponName;
    private String couponImage;
    private int totalQuantity;
    private int remainQuantity;
    private LocalDateTime expiredAt;

    public CouponOneForm(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.couponImage = coupon.getCouponImage();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.expiredAt = coupon.getExpiredAt();
    }


}
