package com.myweapon.hourglass.profile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewPasswordRequest {
    private String currentPassword;
    private String newPassword;
}
