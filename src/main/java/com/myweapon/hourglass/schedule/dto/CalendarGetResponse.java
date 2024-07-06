package com.myweapon.hourglass.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class CalendarGetResponse {
    private List<CalendarRemain> schedules;

    public CalendarGetResponse(List<CalendarRemain> schedules){
        this.schedules = schedules;
    }

    public static CalendarGetResponse of(List<CalendarRemain> schedules){
        return CalendarGetResponse.builder()
                .schedules(schedules)
                .build();
    }
}
