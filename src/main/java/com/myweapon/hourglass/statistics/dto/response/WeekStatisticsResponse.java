package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByWeekDay;
import lombok.Builder;

import java.util.List;

@Builder
public record WeekStatisticsResponse(List<BurstRatioByCategories> byCategories, List<BurstTimeByWeekDay> byWeekDays) {
    public static WeekStatisticsResponse of(List<BurstRatioByCategories> byCategories, List<BurstTimeByWeekDay> byWeekDays){
        return WeekStatisticsResponse.builder()
                .byCategories(byCategories)
                .byWeekDays(byWeekDays)
                .build();
    }
}
