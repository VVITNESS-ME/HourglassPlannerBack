package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.TotalBurstByMonth;

import java.util.List;

public record YearStatisticsResponse(List<BurstRatioByCategories> byCategories, List<TotalBurstByMonth> byMonths) {
}
