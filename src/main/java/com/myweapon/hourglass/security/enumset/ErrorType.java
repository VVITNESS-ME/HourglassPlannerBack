package com.myweapon.hourglass.security.enumset;


import lombok.Getter;

@Getter
public enum ErrorType {
    DUPLICATED_EMAIL(400,"Email already exist"),
    DUPLICATED_NAME(400,"Name already exits"),
    NOT_VALID_TOKEN(400,"Invalid token"),
    NO_JWT(400,"JWT doesn't exist"),
    NO_EMAIL_OR_PASSWORD(400,"Email or password doesn't exist"),
    UNKNOWN_ERROR_AUTHENTICATION_FAILED(400,"Authentication failed because of unknown_error"),
    HOURGLASS_IN_PROGRESS(400,"Hourglass is in progress"),
    HOURGLASS_NOT_EXISTS(400,"Hourglass doesn't exist"),
    HOURGLASS_ALREADY_PAUSE(400,"Hourglass is already paused"),
    HOURGLASS_NOT_PAUSE(400,"Hourglass isn't paused"),
    USER_CATEGORY_NOT_EXISTS(400,"UserCategory doesn't exist"),
    INVALID_REQUEST(400,"request is invalid"),
    NOT_KNOWN_ERROR(400,"error isn't known");

    private int code;
    private String message;

    ErrorType(int code,String message){
        this.code = code;
        this.message = message;
    }
}
