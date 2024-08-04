package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByDay;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByWeekDay;
import lombok.Builder;

import java.util.List;

@Builder
public record MonthStatisticsResponse(List<BurstRatioByCategories> byCategories, List<BurstTimeByDay> byDays) {
    public static MonthStatisticsResponse of(List<BurstRatioByCategories> byCategories, List<BurstTimeByDay> byDays){
        return MonthStatisticsResponse.builder()
                .byCategories(byCategories)
                .byDays(byDays)
                .build();
    }
}
