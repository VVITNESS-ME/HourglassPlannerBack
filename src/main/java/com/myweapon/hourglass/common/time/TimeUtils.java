package com.myweapon.hourglass.common;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

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



    public static DayStartEnd dayStartEnd(LocalDateTime dateTime){
        LocalDate start = dateTime.truncatedTo(ChronoUnit.DAYS).toLocalDate();
        LocalDate end = start.plusDays(1);
        return new DayStartEnd(start,end);
    }

    public static DayStartEnd dayStartEnd(LocalDate date){
        return new DayStartEnd(date,date.plusDays(1));
    }

    public static DayStartEnd todayStartEnd(){
        return dayStartEnd(LocalDateTime.now());
    }
}
