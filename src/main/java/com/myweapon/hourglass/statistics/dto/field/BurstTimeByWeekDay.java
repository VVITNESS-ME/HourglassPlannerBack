package com.myweapon.hourglass.statistics.dto.field;

import com.myweapon.hourglass.common.time.Week;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Builder
public record BurstTimeByWeekDay(String weekDay, Integer burstTime) {
    public static List<BurstTimeByWeekDay> listOf(LocalDate monday, List<BurstTimeByDay> burstTimeByDay){
        Deque<BurstTimeByDay> burstTimeByDaysQueue = new ArrayDeque<>(burstTimeByDay);
        List<BurstTimeByWeekDay> burstByWeekDayOfWeek = new ArrayList<>();
        LocalDate curWeekDay = monday;
        for(Week week : Week.values()){
            if(!burstTimeByDaysQueue.isEmpty() && burstTimeByDaysQueue.peek().isDay(curWeekDay)){
                Integer burstTime = burstTimeByDaysQueue.pop().burstTime();
                burstByWeekDayOfWeek.add(BurstTimeByWeekDay.of(week.getName(),burstTime));
            }
            else{
                burstByWeekDayOfWeek.add(nothingTodoFrom(week.getName()));
            }
            curWeekDay = curWeekDay.plusDays(1);
        }
        return burstByWeekDayOfWeek;
    }

    public static BurstTimeByWeekDay of(String weekDay,Integer burstTime){
        return BurstTimeByWeekDay.builder()
                .weekDay(weekDay)
                .burstTime(burstTime)
                .build();
    }

    private static BurstTimeByWeekDay nothingTodoFrom(String weekDay){
        return of(weekDay,0);
    }
}
