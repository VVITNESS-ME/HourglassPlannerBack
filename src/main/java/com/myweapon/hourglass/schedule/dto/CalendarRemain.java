package com.myweapon.hourglass.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;


@Getter
@Slf4j
public class CalendarRemain {
    private String description;
    private Long dDay;

    public CalendarRemain(String description, LocalDate dueDate){
        this.description = description;
        dDay = ChronoUnit.DAYS.between(LocalDate.now(),dueDate);
    }
}
