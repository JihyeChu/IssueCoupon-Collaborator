package com.coupon.issuecouponservice.security.oauth;

import com.coupon.issuecouponservice.domain.user.Role;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PrincipalOauth2UserService")
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = getUserInfo(userRequest, oAuth2User);

        AtomicBoolean isNewUser = new AtomicBoolean(false);

        User user = userRepository.findByUsername(oAuth2UserInfo.getUsername())
                .orElseGet(() -> {
                    isNewUser.set(true);
                    return registerNewUser(oAuth2UserInfo);
                });

        return new UserDetailsImpl(user, oAuth2User.getAttributes(), isNewUser.get());
    }

    private OAuth2UserInfo getUserInfo(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return switch (registrationId) {
            case "google" -> {
                log.info("구글 로그인");
                yield new GoogleUserInfo(attributes);
            }
            case "kakao" -> {
                log.info("카카오 로그인");
                yield new KakaoUserInfo(attributes);
            }
            case "naver" -> {
                log.info("네이버 로그인");
                yield new NaverUserInfo(attributes);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 플랫폼입니다 : " + registrationId);
        };
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        log.info("최초 로그인 및 회원가입 진행");
        User newUser = User.builder()
                .nickName(oAuth2UserInfo.getNickName())
                .username(oAuth2UserInfo.getUsername())
                .email(oAuth2UserInfo.getEmail())
                .role(Role.USER)
                .provider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getProviderId())
                .build();
        userRepository.save(newUser);
        log.info("회원가입 완료");
        return newUser;
    }
}
