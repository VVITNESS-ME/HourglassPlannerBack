package com.myweapon.hourglass.common;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    @Builder
    private ApiResponse(String code, String message, T response){
        this.code = code;
        this.message = message;
        this.data = response;
    }

    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .code("200")
                .message("success")
                .build();
    }

}
