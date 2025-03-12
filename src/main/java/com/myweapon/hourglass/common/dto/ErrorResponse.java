package com.myweapon.hourglass.common.dto;

import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {
    private int code;
    private String message;

    @Builder
    private ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(ErrorType errorType) {
        return ErrorResponse.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    public static ErrorResponse of(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .code(status.value())
                .message(message)
                .build();
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        String message = "";

        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        return ErrorResponse.of(HttpStatus.BAD_REQUEST, message);
    }
}
