package com.myweapon.hourglass.common.exception;

import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException{
    private final ErrorType errorType;

    public RestApiException(ErrorType errorType){
        this.errorType = errorType;
    }
}
