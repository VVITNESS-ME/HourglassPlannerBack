package com.myweapon.hourglass.statistics.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByCategories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CategoryStatisticsService {
    List<BurstTimeByCategories> getBurstTimeByCategories(LocalDateTime start, LocalDateTime end, User user);

    /**
     * start와 end의 범위에서 카테고리 당 총 공부시간을 반환한다.
     * end의 경계값은 포함하지 않는다.
     * @param date
     * @param user
     * @return
     */
    List<BurstTimeByCategories> getBurstTimeByCategories(LocalDate date, User user);
    List<BurstTimeByCategories> getBurstTimeByCategories(LocalDate start, LocalDate end, User user);
}
