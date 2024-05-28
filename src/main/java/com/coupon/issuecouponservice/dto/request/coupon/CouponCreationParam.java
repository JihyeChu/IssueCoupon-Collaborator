package com.coupon.issuecouponservice.dto.request.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponCreationParam {

    private String couponName;

    private String couponImage;

    private int totalQuantity;

    private int remainQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expiredAt;
}
