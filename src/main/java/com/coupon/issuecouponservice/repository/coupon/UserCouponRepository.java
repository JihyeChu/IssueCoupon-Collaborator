package com.coupon.issuecouponservice.repository.coupon;

import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface

UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("select uc from UserCoupon uc " +
            "join fetch uc.coupon " +
            "where uc.user.id = :userId")
    List<UserCoupon> findUserCouponsByUserId(@Param("userId") Long userId);

    int countByCouponId(Long couponId);
}
