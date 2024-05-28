package com.coupon.issuecouponservice.controller.user;

import com.coupon.issuecouponservice.dto.request.user.UserModificationParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/setup")
    public String setupProfile() {
        return "profile-setup";
    }

    @PatchMapping("/profile/setup")
    @ResponseBody
    public ResponseEntity<ApiResponseForm> modifyUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestBody UserModificationParam param) {

        userService.modifyUserProfile(userDetails.getUser(), param);

        return ResponseEntity.ok().body(new ApiResponseForm("프로필 작성 완료", HttpStatus.OK.value()));
    }

}
