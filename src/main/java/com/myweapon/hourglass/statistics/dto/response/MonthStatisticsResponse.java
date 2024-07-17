package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.TotalBurstByWeekDay;

import java.util.List;

public record MonthStatisticsResponse(List<BurstRatioByCategories> byCategories, List<TotalBurstByWeekDay> byDays) {
}
