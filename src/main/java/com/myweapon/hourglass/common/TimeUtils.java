package com.myweapon.hourglass.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static LocalDateTime formatString(String timeString){
        System.out.println(timeString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        String pureFormat = timeString.replace('T',' ').substring(0,timeString.length()-5);
        System.out.println(pureFormat);
        return LocalDateTime.parse(pureFormat,formatter);
    }
}
