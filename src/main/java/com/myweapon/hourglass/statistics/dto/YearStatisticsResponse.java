package com.myweapon.hourglass.statistics.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class YearStatisticsResponse {
    private List<BurstRatioByCategories> byCategory;
    private List<TotalBurstByMonth> byDays;

    public static YearStatisticsResponse of(List<TotalBurstByMonth> byDays, List<BurstRatioByCategories> byCategory){
        return YearStatisticsResponse.builder()
                .byDays(byDays)
                .byCategory(byCategory)
                .build();
    }
}
