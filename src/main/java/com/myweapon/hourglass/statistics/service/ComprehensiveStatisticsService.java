package com.myweapon.hourglass.statistics.service;
import com.myweapon.hourglass.common.time.WeekDuration;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.*;
import com.myweapon.hourglass.statistics.dto.response.*;
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
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<Integer> byHours = timeFlowStatisticsService.getBurstTimeByHour(date,user);

        return DayStatisticsResponse.of(byCategories,byHours);
    }

    public WeekStatisticsResponse getWeekStatisticsResponse(WeekDuration weekDuration,User user){
        LocalDate monday = weekDuration.monday();
        LocalDate sunday = weekDuration.sunday();

        List<BurstTimeByCategories> burstTimeByCategories
                = categoryStatisticsService
                .getBurstTimeByCategories
                        (weekDuration.monday()
                        ,weekDuration.sunday().plusDays(1)
                        ,user);
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<BurstTimeByDay> burstTimeByDays = timeFlowStatisticsService.getBurstTimeByDay(monday,sunday.plusDays(1),user);
        List<BurstTimeByWeekDay> byWeekDays = BurstTimeByWeekDay.listOf(monday,burstTimeByDays);

        return WeekStatisticsResponse.of(byCategories,byWeekDays);
    }

    /***
     * 입력받은 날짜의 달에 대한 통계를 반환하는 메서드
     * 아무 날짜나 입력해도 그 달에 대한 통계를 반환한다.
     * @param date
     * @param user
     * @return
     */
    public MonthStatisticsResponse getMonthStatisticsResponse(LocalDate date,User user){
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = monthStart.plusMonths(1);

        List<BurstTimeByCategories> burstTimeByCategories = categoryStatisticsService
                .getBurstTimeByCategories(monthStart,monthEnd,user);
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<BurstTimeByDay> burstTimeByDays = timeFlowStatisticsService.getBurstTimeByDay(monthStart,monthEnd,user);


        return MonthStatisticsResponse.of(byCategories,burstTimeByDays);
    }

    /***
     * 입력받은 날짜의 년도에 대한 통계를 반환하는 메서드
     * 아무 날짜나 입력해도 그 년도에 대한 통계를 반환한다.
     * @param date
     * @param user
     * @return
     */
    public YearStatisticsResponse getYearStatisticsResponse(LocalDate date,User user){
        LocalDate yearStart = date.withDayOfYear(1);
        LocalDate yearLast = yearStart.plusYears(1);

        List<BurstTimeByCategories> burstTimeByCategories = categoryStatisticsService
                .getBurstTimeByCategories(yearStart,yearLast,user);
        List<BurstRatioByCategories> byCategories = BurstRatioByCategories.listFrom(burstTimeByCategories);

        List<BurstTimeByMonth> burstTimeByMonths = timeFlowStatisticsService.getBurstTimeByMonthThatYear(date,user);

        return YearStatisticsResponse.of(byCategories,burstTimeByMonths);
    }
}
