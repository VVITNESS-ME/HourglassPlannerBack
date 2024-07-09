package com.myweapon.hourglass.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApiSuccess {
    private final Integer code = 200;
    private final String message = "success";

    public static ApiSuccess body(){
        return new ApiSuccess();
    }
}
