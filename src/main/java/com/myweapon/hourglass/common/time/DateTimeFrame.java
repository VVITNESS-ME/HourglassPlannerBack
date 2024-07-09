package com.myweapon.hourglass.common.time;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Getter
public enum DateTimeFrame {
    FROM_TIME(DateTimeFrameConstants.FROM_TIME, DateTimeFrame::toLocalDateTimeFromTime, DateTimeFrame::toLocalDateFromTime),
    FROM_DAY(DateTimeFrameConstants.FROM_DAY, DateTimeFrame::parseLocalDateTimeFromDate, LocalDate::parse),
    FROM_MONTH(DateTimeFrameConstants.FROM_MONTH, LocalDateTime::parse, LocalDate::parse),
    FROM_YEAR(DateTimeFrameConstants.FROM_YEAR, LocalDateTime::parse, LocalDate::parse);

    private final static Integer TIME_KOREA_FROM_UTC = 9;

    private final String format;
    private final Function<String, LocalDateTime> toLocalDateTime;
    private final Function<String, LocalDate> toLocalDate;

    DateTimeFrame(String format, Function<String,LocalDateTime> toLocalDateTime, Function<String,LocalDate> toLocalDate){
        this.format = format;
        this.toLocalDateTime = toLocalDateTime;
        this.toLocalDate = toLocalDate;
    }

    public String getFormat(){
        return format;
    }

    public LocalDateTime toLocalDateTime(String dateTimeString){
        return toLocalDateTime.apply(dateTimeString);
    }
    public LocalDate toLocalDate(String dateTimeString){
        return toLocalDate.apply(dateTimeString);
    }

    private static LocalDateTime parseLocalDateTimeFromDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FROM_DAY.format);
        LocalDate localDate = LocalDate.parse(dateString,formatter);
        return localDate.atStartOfDay();
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
