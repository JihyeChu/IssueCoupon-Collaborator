package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponQueryService {

    private final UserCouponRepository userCouponRepository;

    public List<UserCoupon> getUserCoupons(Long userId) {
        return userCouponRepository.findUserCouponsByUserId(userId);
    }

    public void saveUserCoupon(UserCoupon userCoupon) {
        userCouponRepository.save(userCoupon);
    }
}
