package com.coupon.issuecouponservice.facade;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.repository.coupon.LockRepository;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final CouponService couponService;


    public void issueCoupon(CouponIssueParam couponIssueParam, User user){
        try {
            lockRepository.getLock(couponIssueParam.getCouponId().toString());
            couponService.issueCoupon(couponIssueParam, user);
        } finally {
            lockRepository.releaseLock(couponIssueParam.getCouponId().toString());
        }
    }

}


