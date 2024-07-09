package com.myweapon.hourglass.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TodoGetResponse {
    private List<Todo> schedules;

    @Builder
    public TodoGetResponse(List<Todo> schedules){
        this.schedules = schedules;
    }

    public static TodoGetResponse of(List<Todo> schedules){
        return TodoGetResponse.builder()
                .schedules(schedules)
                .build();
    }
}
