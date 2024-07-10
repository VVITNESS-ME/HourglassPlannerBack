package com.myweapon.hourglass.common.time;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;

import java.time.LocalDate;
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
        LocalDateTime standard_time;
        try{
            String pureFormat = timeString.replace('T',' ').substring(0,timeString.length()-5);
            standard_time =  LocalDateTime.parse(pureFormat,formatter);
        }catch (Exception e){
            throw new RestApiException(ErrorType.INVALID_REQUEST);
        }
        return standard_time.plusHours(hours);
    }



    public static DateStartEnd dayStartEnd(LocalDateTime dateTime){
        LocalDate start = dateTime.truncatedTo(ChronoUnit.DAYS).toLocalDate();
        return DateStartEnd.of(start,1);
    }

    public static DateStartEnd todayStartEnd(){
        return dayStartEnd(LocalDateTime.now());
    }
}
