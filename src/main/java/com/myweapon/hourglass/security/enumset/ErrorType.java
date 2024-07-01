package com.myweapon.hourglass.security.enumset;


import lombok.Getter;

@Getter
public enum ErrorType {
    NOT_VALID_TOKEN(400,"invalid token"),
    DUPLICATED_EMAIL(400,"email already exist"),
    DUPLICATED_NAME(400,"name already exits"),
    EXPIRED_JWT(400,"token is expired");

    private int code;
    private String message;

    ErrorType(int code,String message){
        this.code = code;
        this.message = message;
    }
}
