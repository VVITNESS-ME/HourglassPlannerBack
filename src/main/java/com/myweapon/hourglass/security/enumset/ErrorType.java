package com.myweapon.hourglass.security.enumset;


import lombok.Getter;

@Getter
public enum ErrorType {
    DUPLICATED_EMAIL(400,"email already exist"),
    DUPLICATED_NAME(400,"name already exits"),
    NOT_VALID_TOKEN(400,"invalid token"),
    NO_JWT(400,"jwt doesn't exist"),
    NO_EMAIL_OR_PASSWORD(400,"email or password doesn't exist"),
    UNKNOWN_ERROR(400,"unknown_error"),
    HOURGLASS_IN_PROGRESS(400,"hourglass is in progress"),
    HOURGLASS_NOT_EXISTS(400,"hourglass doesn't exist"),
    HOURGLASS_ALREADY_PAUSE(400,"hourglass is already paused"),
    HOURGLASS_NOT_PAUSE(400,"hourglass isn't paused");

    private int code;
    private String message;

    ErrorType(int code,String message){
        this.code = code;
        this.message = message;
    }
}
