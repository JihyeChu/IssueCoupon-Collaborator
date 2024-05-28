package com.coupon.issuecouponservice.dto.request.coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponIssueParam {

    private Long couponId;

    public CouponIssueParam(Long couponId) {
        this.couponId = couponId;
    }
}
