package com.myweapon.hourglass.statistics.repository;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByCategories;
import com.myweapon.hourglass.statistics.entity.UserHourglassView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudyStatisticsViewRepository extends JpaRepository<UserHourglassView,Long> {

    @Query(value = "select new com.myweapon.hourglass.statistics.dto.field.BurstTimeByCategories( s.categoryName" +
            ",sum(s.burstTime)" +
            ",s.color )" +
            "from StudyStaticsView s " +
            "where s.userId = :userId and s.end between :start and :end " +
            "group by s.userCategoryId")
    public List<BurstTimeByCategories> getCategoryBurstTimeByCategories
            (@Param("start") LocalDateTime start, @Param("end")  LocalDateTime end, User user);
}
