package com.myweapon.hourglass.security.enumset;


import lombok.Getter;

@Getter
public enum ErrorType {
    DUPLICATED_EMAIL(400,"email already exist"),
    DUPLICATED_NAME(400,"name already exits"),
    NOT_VALID_TOKEN(400,"invalid token"),
    NO_JWT(400,"jwt doesn't exist"),
    NO_EMAIL_OR_PASSWORD(400,"email or password doesn't exist"),
    UNKNOWN_ERROR(400,"unknown_error");

    private int code;
    private String message;

    ErrorType(int code,String message){
        this.code = code;
        this.message = message;
    }
}
