package com.myweapon.hourglass.common.time;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static LocalDateTime stringIsoToLocalDateTime(String valueString){
        ZonedDateTime zonedDateTime = OffsetDateTime.parse(valueString)
                .atZoneSameInstant(ZoneId.of("Asia/Seoul"));
        return zonedDateTime.toLocalDateTime();
    }
    public static LocalDate stringToLocalDate(String valueString){
        return LocalDate.parse(valueString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
