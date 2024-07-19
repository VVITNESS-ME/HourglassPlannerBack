package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByMonth;

import java.util.List;

public record YearStatisticsResponse(List<BurstRatioByCategories> byCategories, List<BurstTimeByMonth> byMonths) {
}
