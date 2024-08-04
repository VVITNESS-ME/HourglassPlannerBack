package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByDay;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByMonth;
import com.myweapon.hourglass.statistics.dto.response.DayStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.dto.response.MonthStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.YearStatisticsResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeFlowStatisticsService {

    public List<Integer> getBurstTimeByHour(LocalDate date, User user);
    public List<BurstTimeByDay> getBurstTimeByDay(LocalDate start,LocalDate end, User user);

    public List<BurstTimeByMonth> getBurstTimeByMonthThatYear(LocalDate date, User user);
}
