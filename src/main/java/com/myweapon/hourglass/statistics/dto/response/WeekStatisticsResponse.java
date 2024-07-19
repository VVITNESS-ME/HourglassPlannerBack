package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByWeekDay;

import java.util.List;

public record WeekStatisticsResponse(List<BurstRatioByCategories> byCategories,List<BurstTimeByWeekDay> byWeekDays) {
}
