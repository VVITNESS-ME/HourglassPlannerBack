package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import lombok.Builder;

import java.util.List;

@Builder
public record  DayStatisticsResponse(List<BurstRatioByCategories> byCategories, List<Integer> byHours) {
    public static DayStatisticsResponse of(List<BurstRatioByCategories> byCategories, List<Integer> byHours){
        return DayStatisticsResponse.builder()
                .byCategories(byCategories)
                .byHours(byHours)
                .build();
    }
}
