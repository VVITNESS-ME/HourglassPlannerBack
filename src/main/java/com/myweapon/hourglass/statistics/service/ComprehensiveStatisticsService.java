package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstRatioByCategories;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByCategories;
import com.myweapon.hourglass.statistics.dto.response.DayStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.dto.response.MonthStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.WeekStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        List<BurstRatioByCategories> burstRatioByCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<Integer> statisticsByHours = timeFlowStatisticsService.getBurstTimeByHour(date,user);

        return DayStatisticsResponse.builder()
                .byCategories(burstRatioByCategories)
                .byHours(statisticsByHours)
                .build();
    }

    public WeekStatisticsResponse getWeekStatisticsResponse(){

    }

//    public MonthStatisticsResponse getMonthStatisticsResponse(){
//
//
//    }
}
