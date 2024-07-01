package com.myweapon.hourglass.Exception;

import com.myweapon.hourglass.security.enumset.ErrorType;

public class JwtException extends RuntimeException{
    private final ErrorType errorType;

    public JwtException(ErrorType errorType){
        this.errorType = errorType;
    }
}
