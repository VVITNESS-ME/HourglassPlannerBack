package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.enumset.DateRangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Getter
public class DateTimeRange {
    private LocalDateTime start;
    private LocalDateTime end;

    public String toString(){
        return start.toString()+"~"+end.toString();
    }

    public boolean equals(Object obj){
        DateTimeRange dateTimeRange = (DateTimeRange) obj;
        return start.isEqual(dateTimeRange.getStart()) && end.isEqual(dateTimeRange.getEnd());
    }

    public static DateTimeRange of(LocalDateTime start,LocalDateTime end){
        return DateTimeRange
                .builder()
                .start(start)
                .end(end)
                .build();
    }

    public static DateTimeRange range(DateRangeType rangeType, LocalDate localDate){
        LocalDateTime startOfDay = localDate.atStartOfDay();
        DateTimeRange range = DateTimeRange.dayRange(startOfDay);
        if(rangeType.equals(DateRangeType.WEEK)){
            range = DateTimeRange.weekRange(startOfDay);
        }else if(rangeType.equals(DateRangeType.MONTH)){
            range = DateTimeRange.monthRange(startOfDay);
        }else if(rangeType.equals(DateRangeType.YEAR)){
            range = DateTimeRange.yearRange(startOfDay);
        }
        return range;
    }

    public static DateTimeRange dayRange(LocalDateTime localDateTime){
        LocalDateTime startOfDay = convertStartOfDay(localDateTime);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return of(startOfDay,endOfDay);
    }

    /*
    일주일 범위의 객체를 반환한다.
     */
    public static DateTimeRange weekRange(LocalDateTime localDateTime){
        LocalDateTime startOfWeek = convertStartOfDay(localDateTime).with(DayOfWeek.SUNDAY);
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1).minusSeconds(1);
        return of(startOfWeek,endOfWeek);
    }

    public static DateTimeRange monthRange(LocalDateTime localDateTime){
        LocalDateTime startOfMonth = convertStartOfDay(localDateTime).withDayOfMonth(1);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return of(startOfMonth,endOfMonth);
    }
    public static DateTimeRange yearRange(LocalDateTime localDateTime){
        LocalDateTime startOfYear = convertStartOfDay(localDateTime).withDayOfYear(1);
        LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);
        return of(startOfYear,endOfYear);
    }


    public static LocalDateTime convertStartOfDay(LocalDateTime localDateTime){

        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(0,0,0));
    }
}
