package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.response.DayStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.dto.response.MonthStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.YearStatisticsResponse;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {
    GardenResponse getGardens(User user);
    DayStatisticsResponse getDayStatistics(LocalDate date, User user);
    MonthStatisticsResponse getMonthStatistics(LocalDate date,User user);
    YearStatisticsResponse getYearStatistics(LocalDate date, User user);
}
