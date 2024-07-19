package com.myweapon.hourglass.statics.service;

import com.myweapon.hourglass.common.time.Week;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statics.dto.*;
import com.myweapon.hourglass.statics.repository.StudyStatisticsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl {
    private final StudyStatisticsViewRepository studyStatisticsViewRepository;
    public List<TotalBurstByDay> calculateTotalBurstByDaysOf(LocalDateTime start, LocalDateTime end, User user){
        return studyStatisticsViewRepository.calculateTotalBurstByDaysOf(start,end,user.getId());
    }

    public List<TotalBurstByMonth> calculateTotalBurstByMonthOf(LocalDateTime start,LocalDateTime end, User user){
        return studyStatisticsViewRepository.calculateTotalBurstByMonthOf(start,end, user.getId());
    }

    public List<TotalBurstByWeekDay> calculateTotalBurstByWeekDays(LocalDateTime monday, User user){
        LocalDateTime sunday = monday.plusDays(6);
        Deque<TotalBurstByDay> totalBurstByDayOfWeek = new ArrayDeque<>(calculateTotalBurstByDaysOf(monday,sunday,user));

        LocalDate curWeekDay = monday.toLocalDate();
        List<TotalBurstByWeekDay> totalBurstByWeekDayOfWeek = new ArrayList<>();
        for(Week week : Week.values()){
            if(!totalBurstByDayOfWeek.isEmpty() && totalBurstByDayOfWeek.peek().isDate(curWeekDay)){
                Long totalBurst = totalBurstByDayOfWeek.pop().getTotalBurst();
                totalBurstByWeekDayOfWeek.add(TotalBurstByWeekDay.of(week,totalBurst));
            }
            else{
                totalBurstByWeekDayOfWeek.add(TotalBurstByWeekDay.nothingTodoFrom(week));
            }
            curWeekDay = curWeekDay.plusDays(1);
        }
        return totalBurstByWeekDayOfWeek;
    }

    public List<BurstTimeByCategories> calculateBurstTimeByCategoryOf(LocalDateTime start, LocalDateTime end, User user){
        return studyStatisticsViewRepository.calculateTotalBurstByCategoriesOf(start,end,user.getId());
    }

    public List<BurstRatioByCategories> calculateBurstRatioByCategoryOf(LocalDateTime start,LocalDateTime end,User user){
        List<BurstTimeByCategories> burstTimesByCategories = calculateBurstTimeByCategoryOf(start,end,user);

        return BurstRatioByCategories.listFrom(burstTimesByCategories);
    }
}
