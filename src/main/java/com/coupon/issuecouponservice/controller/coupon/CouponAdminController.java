package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured(ADMIN)
public class CouponAdminController {

    private final CouponService couponService;

    @PostMapping("/coupon")
    public void createCoupon(@RequestBody CouponCreationParam param) {

        couponService.createCoupon(param);

    }

    @PatchMapping("/coupon/{couponId}")
    public void modifyCoupon(@PathVariable("couponId") Long couponId, @RequestBody CouponModificationParam param) {

        couponService.modifyCoupon(couponId, param);

    }

    @DeleteMapping("/coupon/{couponId}")
    public void deleteCoupon(@PathVariable("couponId") Long couponId) {

        couponService.deleteCoupon(couponId);

    }
}
