package com.myweapon.hourglass.common.time;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class DateStartEnd {
    private LocalDate start;
    private LocalDate end;

    public static DateStartEnd of(LocalDate start, Integer day){
        return DateStartEnd.builder()
                .start(start)
                .end(start.plusDays(day))
                .build();
    }

    public static DateStartEnd monthOf(LocalDate start, Integer month){
        return DateStartEnd.builder()
                .start(start)
                .end(start.plusMonths(month).minusDays(1))
                .build();
    }

    public LocalDateTime getStartWithTime(){
        return start.atStartOfDay();
    }
    public LocalDateTime getEndWithTime(){
        return end.atStartOfDay();
    }


}
