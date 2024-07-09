package com.myweapon.hourglass.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class CalendarPostResponse {
    private Long cId;

    public CalendarPostResponse(Long cId){
        this.cId = cId;
    }

    public static CalendarPostResponse of(Long cId){
        return CalendarPostResponse.builder()
                .cId(cId)
                .build();
    }
}
