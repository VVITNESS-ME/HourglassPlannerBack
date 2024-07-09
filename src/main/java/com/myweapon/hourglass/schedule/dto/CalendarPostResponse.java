package com.myweapon.hourglass.schedule.dto;

import com.myweapon.hourglass.schedule.entity.Calender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Builder
public class CalendarPostResponse {
    private List<Long> cIds;

    public CalendarPostResponse(List<Long> cIds){
        this.cIds = cIds;
    }

    public static CalendarPostResponse of(List<Calender> calendarsSaved){
        List<Long> cIds = calendarsSaved.stream().map(Calender::getId).toList();

        return CalendarPostResponse.builder()
                .cIds(cIds)
                .build();
    }
}
