package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByCategories;
import com.myweapon.hourglass.statistics.repository.StudyStatisticsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleCategoryStatisticsService implements CategoryStatisticsService{
    private final StudyStatisticsViewRepository studyStatisticsViewRepository;
    @Override
    public List<BurstTimeByCategories> getBurstTimeByCategories(LocalDateTime start, LocalDateTime end, User user) {
        return studyStatisticsViewRepository.getCategoryBurstTimeByCategories(start,end,user);
    }

    @Override
    public List<BurstTimeByCategories> getBurstTimeByCategories(LocalDate date, User user) {
        return getBurstTimeByCategories(date,date.plusDays(1),user);
    }

    @Override
    public List<BurstTimeByCategories> getBurstTimeByCategories(LocalDate start, LocalDate end, User user) {
        LocalDateTime startTime = start.atStartOfDay();
        LocalDateTime endTime = end.atStartOfDay().minusNanos(1);
        return getBurstTimeByCategories(startTime,endTime,user);
    }
}
