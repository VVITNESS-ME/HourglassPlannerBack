package com.myweapon.hourglass.security.dto;

import com.myweapon.hourglass.security.entity.User;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;

    public static UserResponseDto of(User save) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.id = save.getId();
        userResponseDto.name = save.getName();
        userResponseDto.email = save.getEmail();
        return userResponseDto;
    }
}
