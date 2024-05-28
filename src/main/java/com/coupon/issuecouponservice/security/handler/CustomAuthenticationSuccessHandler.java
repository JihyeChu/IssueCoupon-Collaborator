package com.coupon.issuecouponservice.security.handler;

import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "CustomAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 기본 url
        setDefaultTargetUrl("/");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        boolean isNewUser = userDetails.isNewUser();

        if (isNewUser) {
            log.info("최초 이용자 추가 정보 기입");
            getRedirectStrategy().sendRedirect(request, response, "/user/profile/setup");
        } else {
            log.info("기존 이용자 인덱스 페이지 이동");
            getRedirectStrategy().sendRedirect(request, response, "/");
        }
    }
}
