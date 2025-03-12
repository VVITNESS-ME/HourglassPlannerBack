package com.myweapon.hourglass.timer.exception;

public class DefaultCategoryException extends RuntimeException{
    public static final String NoSuchDefaultCategory = "매치되는 디폴트 카테고리가 존재하지 않습니다.";
    public DefaultCategoryException(String message){
        super(message);
    }
    public DefaultCategoryException of(String message){
        return new DefaultCategoryException(message);
    }
}
