package com.myweapon.hourglass.exception;

import com.myweapon.hourglass.security.enumset.ErrorType;

public class JwtException extends RuntimeException{
    private final ErrorType errorType;

    public JwtException(ErrorType errorType){
        this.errorType = errorType;
    }
}
