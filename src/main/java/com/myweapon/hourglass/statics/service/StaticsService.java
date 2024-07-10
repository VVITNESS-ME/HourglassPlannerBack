package com.myweapon.hourglass.statics.service;

import com.myweapon.hourglass.common.time.Week;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statics.dto.BurstRatioByCategories;
import com.myweapon.hourglass.statics.dto.BurstTimeByCategories;
import com.myweapon.hourglass.statics.dto.TotalBurstByDay;
import com.myweapon.hourglass.statics.dto.TotalBurstByWeekDay;
import com.myweapon.hourglass.statics.repository.StudyStaticsViewRepository;
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
public class StaticsService {
    private final StudyStaticsViewRepository studyStaticsViewRepository;
    public List<TotalBurstByDay> calculateTotalBurstByDaysOf(LocalDateTime start, LocalDateTime end, User user){
        return studyStaticsViewRepository.calculateTotalBurstByDaysOf(start,end,user.getId());
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
        return studyStaticsViewRepository.calculateTotalBurstByCategoriesOf(start,end,user.getId());
    }

    public List<BurstRatioByCategories> calculateBurstRatioByCategoryOf(LocalDateTime start,LocalDateTime end,User user){
        List<BurstTimeByCategories> burstTimesByCategories = calculateBurstTimeByCategoryOf(start,end,user);

        return BurstRatioByCategories.listFrom(burstTimesByCategories);
    }
}
