package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.common.time.Week;
import com.myweapon.hourglass.common.time.WeekDuration;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statics.dto.TotalBurstByWeekDay;
import com.myweapon.hourglass.statistics.dto.field.*;
import com.myweapon.hourglass.statistics.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComprehensiveStatisticsService {
    private final TimeFlowStatisticsService timeFlowStatisticsService;
    private final CategoryStatisticsService categoryStatisticsService;

    public GardenResponse getGardensDuring(LocalDate start, LocalDate end, User user){
        return GardenResponse.of(timeFlowStatisticsService.getBurstTimeByDay(start,end,user));
    }

    public DayStatisticsResponse getDayStatisticsResponse(LocalDate date, User user){
        List<BurstTimeByCategories> burstTimeByCategories = categoryStatisticsService.getBurstTimeByCategories(date,user);
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<Integer> byHours = timeFlowStatisticsService.getBurstTimeByHour(date,user);

        return DayStatisticsResponse.of(byCategories,byHours);
    }

    public WeekStatisticsResponse getWeekStatisticsResponse(WeekDuration weekDuration,User user){
        LocalDate monday = weekDuration.monday();
        LocalDate sunday = weekDuration.sunday();

        List<BurstTimeByCategories> burstTimeByCategories = categoryStatisticsService
                .getBurstTimeByCategories(weekDuration.monday(),weekDuration.sunday().plusDays(1),user);
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<BurstTimeByDay> burstTimeByDays = timeFlowStatisticsService.getBurstTimeByDay(monday,sunday.plusDays(1),user);
        List<BurstTimeByWeekDay> byWeekDays = BurstTimeByWeekDay.listOf(monday,burstTimeByDays);

        return WeekStatisticsResponse.of(byCategories,byWeekDays);
    }

    public MonthStatisticsResponse getMonthStatisticsResponse(LocalDate date,User user){
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = date.plusMonths(1);

        List<BurstTimeByCategories> burstTimeByCategories = categoryStatisticsService
                .getBurstTimeByCategories(monthStart,monthEnd,user);
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<BurstTimeByDay> burstTimeByDays = timeFlowStatisticsService.getBurstTimeByDay(monthStart,monthEnd,user);
//        List<BurstTimeByMonth> byMonths = burstTimeByDays.stream()
//                .map(BurstTimeByMonth::of)
//                .toList();

        return MonthStatisticsResponse.of(byCategories,burstTimeByDays);
    }

//    public YearStatisticsResponse getYearStatisticsResponse(LocalDate date,User user){
//
//    }
}
