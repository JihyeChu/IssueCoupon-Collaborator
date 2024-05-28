package com.coupon.issuecouponservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseForm {
    private String msg;
    private Integer statusCode;

    public ApiResponseForm(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
