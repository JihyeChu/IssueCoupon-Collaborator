package com.coupon.issuecouponservice.security.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    private Map<String, Object> properties;
    private Map<String, Object> kakao_account;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getNickName() {
        properties = (Map)attributes.get("properties");
        return properties.get("nickname").toString();
    }

    @Override
    public String getUsername() {
        return "kakao" + "_" + attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        kakao_account = (Map)attributes.get("kakao_account");
        return kakao_account.get("email").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }
}
