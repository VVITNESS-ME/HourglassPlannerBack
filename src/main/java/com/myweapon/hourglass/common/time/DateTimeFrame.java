package com.myweapon.hourglass.common.time;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Getter
public enum DateTimeFormat {
    FROM_TIME("yyyy-MM-dd HH:mm:ss",DateTimeFormat::toLocalDateTimeFromTime,DateTimeFormat::toLocalDateFromTime),
    FROM_DAY("yyyy-MM-dd",LocalDateTime::parse,LocalDate::parse),
    FROM_MONTH("yyyy-MM",LocalDateTime::parse,LocalDate::parse),
    FROM_YEAR("yyyy",LocalDateTime::parse,LocalDate::parse);
    private final static Integer TIME_KOREA_FROM_UTC = 9;
    private final String format;
    private final Function<String, LocalDateTime> toLocalDateTime;
    private final Function<String, LocalDate> toLocalDate;
    DateTimeFormat(String format,Function<String,LocalDateTime> toLocalDateTime,Function<String,LocalDate> toLocalDate){
        this.format = format;
        this.toLocalDateTime = toLocalDateTime;
        this.toLocalDate = toLocalDate;
    }

    private static LocalDateTime toLocalDateTimeFromTime(String dateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FROM_TIME.format);
        LocalDateTime standard_time;
        try{
            String pureFormat = dateTimeString.replace('T',' ').substring(0,dateTimeString.length()-5);
            standard_time =  LocalDateTime.parse(pureFormat,formatter);
        }catch (Exception e){
            throw new RestApiException(ErrorType.INVALID_REQUEST);
        }
        return standard_time.plusHours(TIME_KOREA_FROM_UTC);
    }

    private static LocalDate toLocalDateFromTime(String dateTimeString){
        LocalDateTime dateTime = toLocalDateTimeFromTime(dateTimeString);
        return dateTime.toLocalDate();
    }

}
