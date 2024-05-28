package com.coupon.issuecouponservice.security.oauth;

public interface OAuth2UserInfo {
    String getNickName();
    String getUsername();
    String getEmail();
    String getProvider();
    String getProviderId();
}