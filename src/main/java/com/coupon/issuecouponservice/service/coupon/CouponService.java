package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponQueryService userCouponQueryService;

    // 쿠폰 생성
    public void createCoupon(CouponCreationParam param) {
        // 쿠폰 이름 중복 검증
        checkForDuplicateCouponName(param.getCouponName());

        // 쿠폰 생성
        Coupon coupon = Coupon.CreateCoupon(param);

        couponRepository.save(coupon);
    }

    // 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public List<CouponForm> readAllCoupons() {
        List<Coupon> findCoupons = couponRepository.findAllCoupons();

        return findCoupons.stream()
                .map(CouponForm::new)
                .collect(Collectors.toList());
    }

    // 쿠폰 수정
    public void modifyCoupon(Long couponId, CouponModificationParam param) {
        // 쿠폰 이름 중복 검증
        checkForDuplicateCouponName(param.getCouponName());

        // 쿠폰 조회
        Coupon findCoupon = getCoupon(couponId);

        // 쿠폰 수정
        findCoupon.modifyCoupon(param);
    }

    // 쿠폰 삭제
    public void deleteCoupon(Long couponId) {
        // 쿠폰 조회
        Coupon findCoupon = getCoupon(couponId);

        // 쿠폰 삭제
        findCoupon.deleteCoupon();
    }

    // 쿠폰 상세 조회
    @Transactional(readOnly = true)
    public CouponOneForm selectCoupon(Long couponId) {
        // 쿠폰 조회
        Coupon coupon = getCoupon(couponId);

        // 쿠폰 반환
        return new CouponOneForm(coupon);
    }

    // 쿠폰 발급
    public void issueCoupon(CouponIssueParam couponIssueParam, User user) {
        // 쿠폰 조회
        Coupon coupon = getCoupon(couponIssueParam.getCouponId());

        // 쿠폰 발급
        UserCoupon userCoupon = UserCoupon.CreateUserCoupon(coupon, user);

        userCouponQueryService.saveUserCoupon(userCoupon);
    }

    // 사용자 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public List<CouponForm> readAllUserCoupons(Long userId) {

        // 쿠폰 목록 조회
        List<UserCoupon> findUserCoupons = userCouponQueryService.getUserCoupons(userId);

        // 쿠폰 반환
        return findUserCoupons.stream()
                .map(uc -> new CouponForm(uc.getCoupon()))
                .collect(Collectors.toList());
    }


    // 쿠폰 조회 메서드
    private Coupon getCoupon(Long couponId) {
        return couponRepository.findOneCouponByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    // 쿠폰 검증 메서드
    private void checkForDuplicateCouponName(String couponName) {
        boolean exists = couponRepository.existsByCouponName(couponName);

        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 쿠폰명입니다.");
        }
    }


}
