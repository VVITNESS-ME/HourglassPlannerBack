package com.myweapon.hourglass.statistics.dto;

import com.myweapon.hourglass.common.time.Week;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class TotalBurstByWeekDay {
    private String weekDay;
    private Long totalBurst;

    public static TotalBurstByWeekDay of(Week week, Long totalBurst){
        return TotalBurstByWeekDay.builder()
                .weekDay(week.getName())
                .totalBurst(totalBurst)
                .build();
    }

    public static TotalBurstByWeekDay nothingTodoFrom(Week week){
        return TotalBurstByWeekDay.builder()
                .weekDay(week.getName())
                .totalBurst(0L)
                .build();
    }
}
