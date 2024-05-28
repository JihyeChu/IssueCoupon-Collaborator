package com.coupon.issuecouponservice.dto.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserModificationParam {
    private String image;
    private String nickName;
    private boolean registerAsAdmin;
}
