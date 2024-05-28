package com.coupon.issuecouponservice.security.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    private Map<String, Object> attributesResponse;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
        this.attributesResponse = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getNickName() {
        return attributesResponse.get("name").toString();
    }

    @Override
    public String getUsername() {
        return "naver" + "_" + attributesResponse.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attributesResponse.get("email").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attributesResponse.get("id").toString();
    }
}
