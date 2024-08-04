package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByMonth;
import lombok.Builder;

import java.util.List;

@Builder
public record YearStatisticsResponse(List<BurstRatioByCategories> byCategories, List<BurstTimeByMonth> byMonths) {
    public static YearStatisticsResponse of(List<BurstRatioByCategories> byCategories, List<BurstTimeByMonth> byMonths){
        return YearStatisticsResponse.builder()
                .byCategories(byCategories)
                .byMonths(byMonths).build();
    }
}
