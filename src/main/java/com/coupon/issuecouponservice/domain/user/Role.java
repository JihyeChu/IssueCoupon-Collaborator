package com.coupon.issuecouponservice.domain.user;

public enum Role {

    USER(Authority.USER),  // 고객
    ADMIN(Authority.ADMIN);  // 관리자

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
