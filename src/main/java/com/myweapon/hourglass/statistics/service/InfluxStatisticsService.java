package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.response.DayStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.dto.response.MonthStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.YearStatisticsResponse;

import java.time.LocalDate;

public class InfluxStatisticsService implements StatisticsService{

    @Override
    public GardenResponse getGardens(User user) {
        return null;
    }

    @Override
    public DayStatisticsResponse getDayStatistics(LocalDate date, User user) {
        return null;
    }

    @Override
    public MonthStatisticsResponse getMonthStatistics(LocalDate date, User user) {
        return null;
    }

    @Override
    public YearStatisticsResponse getYearStatistics(LocalDate date, User user) {
        return null;
    }
}
