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
    NOT_KNOWN_ERROR(400,"error isn't known"),
    TASK_NOT_EXISTS(400,"task doesn't exist"),
    PASSWORD_NOT_CORRESPOND(400,"password doesn't correspond"),
    ROOM_IS_FULL(400,"정원 초과입니다."),
    NOT_VALID_ROOM_PASSWORD(400,"비밀번호가 잘못되었습니다."),
    ROOM_IS_NOT_FOUND(400,"방을 찾을수 없습니다."),
    DELETED_USER(400,"user is deleted"),
    GPT_ERROR(400,"gpt_error"),
    TITLE_IS_NOT_ACHIEVED(400,"소지하고 있지 않은 업적입니다."),
    GPT_RESPONSE_ERROR(400,"gpt response abnormally");

    private final int code;
    private final String message;

    ErrorType(int code,String message){
        this.code = code;
        this.message = message;
    }
}
