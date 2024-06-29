package com.myweapon.hourglass.security.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String code;
    private T data;

    @Builder
    private ApiResponse(String code,T response){
        this.code = code;
        this.data = response;
    }
}
