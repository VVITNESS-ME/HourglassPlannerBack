package com.myweapon.hourglass.timer.exception;

public class TimerRestApiException extends RuntimeException{
    //데이터베이스 관련 예외
    public static final String NoSuchHourglassState = "모래시계에는 해당 상태는 존재하지 않습니다.";
    public static final String NoSuchHourglassHistory = "모래시계 기록이 존재하지 않습니다.";
    public static final String NoSuchHourglass = "해당 모래시계가 존재하지 않습니다.";

    public static final String NoSuchHourglassInProgress = "진행중인 모래시계가 존재하지 않습니다.";

    public static final String NoSuchTask = "해당 태스크는 존재하지 않습니다.";
    public static final String NoSuchUserCategory = "해당 유저 카테고리는 존재하지 않습니다.";

    public static final String NoSuchCategoryWithName = "해당 이름을 가진 카테고리는 없습니다.";

    //시간 관련 예외
    public static final String StartTimeMustLateThanBeforeHourglassHistory = "모래시계 시작 시간은 항상 모래시계 기록보다 늦어야 합니다.";
    public static final String RequestTimeEarlier = "새로운 요청 시간은 마지막 기록보다 늦어야 합니다.";
    public static final String HourglassInProgress = "이미 모래시계가 진행중입니다. 모래시계는 하나만 진행할 수 있습니다.";

    //모래시계 상태 관련 예외
    public static final String StateOnlyProgress = "state는 progress만 가능합니다.";
    public static final String HourglassIsEnd = "이미 종료된 모래시계 입니다.";
    public static final String CannotPause = "중지할 수 없습니다.";
    public static final String CannotRestart = "재시작할 수 없습니다.";

    public static final String NoSuchDateRangeType = "해당하는 DateRangeType이 존재하지 않습니다.";

    public TimerRestApiException(String message){
        super(message);
    }

    public static TimerRestApiException of(String message){
        return new TimerRestApiException(message);
    }
}
