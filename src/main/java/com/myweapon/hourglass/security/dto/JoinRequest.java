package com.myweapon.hourglass.security.dto;

import com.myweapon.hourglass.security.entity.User;
import lombok.Data;

@Data
public class JoinRequest {
    private String email;
    private String password;
    private String name;

    public User toEntity(String encode) {
        return User.builder()
                .email(email)
                .password(encode)
                .name(name)
                .build();
    }
}
