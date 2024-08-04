package com.myweapon.hourglass.statistics_legacy.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class DailyStatisticsResponse {
    private List<BurstRatioByCategories> byCategory;
    private List<TotalBurstByWeekDay> byDays;

    public static DailyStatisticsResponse of(List<TotalBurstByWeekDay> byDays, List<BurstRatioByCategories> byCategory){
        return DailyStatisticsResponse.builder()
                .byDays(byDays)
                .byCategory(byCategory)
                .build();
    }
}
