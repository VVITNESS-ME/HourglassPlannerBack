package com.myweapon.hourglass.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static LocalDateTime inputFormatString(String timeString){
        return formatString(timeString,9);
    }
    public static LocalDateTime formatString(String timeString,int hours){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        String pureFormat = timeString.replace('T',' ').substring(0,timeString.length()-5);
        LocalDateTime standard_time =  LocalDateTime.parse(pureFormat,formatter);
        return standard_time.plusHours(hours);
    }

    public static TodayStartEnd todayStartEnd(){
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plusDays(1);
        return new TodayStartEnd(start,end);
    }

    @AllArgsConstructor
    @Getter
    public static class TodayStartEnd{
        private LocalDateTime start;
        private LocalDateTime end;
    }
}
