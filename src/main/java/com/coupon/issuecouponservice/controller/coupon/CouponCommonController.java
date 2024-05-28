package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponCommonController {

    private final CouponService couponService;

    // 쿠폰 전체 조회
    @GetMapping("/coupon")
    public List<CouponForm> readAllCoupons() {

        return couponService.readAllCoupons();

    }

    // 쿠폰 상세 조회
    @GetMapping("/coupon/{couponId}")
    public CouponOneForm readOneCoupon(@PathVariable Long couponId){
        return couponService.selectCoupon(couponId);
    }

}
