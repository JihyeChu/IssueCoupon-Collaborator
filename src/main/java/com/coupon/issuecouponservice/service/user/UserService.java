package com.coupon.issuecouponservice.service.user;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.user.UserModificationParam;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "User Service")
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 프로필 수정
    public void modifyUserProfile(User user, UserModificationParam param) {
        User findUser = getUser(user);

        findUser.modifyUserDetails(param);
    }

    private User getUser(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }
}
