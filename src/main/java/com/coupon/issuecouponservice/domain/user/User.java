package com.coupon.issuecouponservice.domain.user;

import com.coupon.issuecouponservice.domain.common.Timestamped;
import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import com.coupon.issuecouponservice.dto.request.user.UserModificationParam;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @Id
    @Tsid
    private Long id;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image")
    private String image;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @Builder
    public User(String nickName, String username, String email, Role role, String provider, String providerId) {
        this.nickName = nickName;
        this.username = username;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void modifyUserDetails(UserModificationParam param) {
        if (!param.getImage().isBlank() && !this.image.equals(param.getImage())) {
            this.image = param.getImage();
        }

        if (!param.getNickName().isBlank() && !this.nickName.equals(param.getNickName())) {
            this.nickName = param.getNickName();
        }

        if (param.isRegisterAsAdmin()) {
            this.role = Role.ADMIN;
        }
    }
}
