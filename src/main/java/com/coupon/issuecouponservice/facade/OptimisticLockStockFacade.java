package com.coupon.issuecouponservice.facade;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final CouponService couponService;

    public void issueCoupon(CouponIssueParam couponIssueParam, User user) throws InterruptedException {
        while (true) {
            try {
                couponService.issueCoupon(couponIssueParam, user);
                break;
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
