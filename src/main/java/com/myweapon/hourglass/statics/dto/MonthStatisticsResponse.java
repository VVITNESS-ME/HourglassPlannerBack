package com.myweapon.hourglass.statics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class MonthStatisticsResponse {
    private List<BurstRatioByCategories> byCategory;
    private List<TotalBurstByDay> byDays;

    public static MonthStatisticsResponse of(List<TotalBurstByDay> byDays, List<BurstRatioByCategories> byCategory){
        return MonthStatisticsResponse.builder()
                .byDays(byDays)
                .byCategory(byCategory)
                .build();
    }
}


