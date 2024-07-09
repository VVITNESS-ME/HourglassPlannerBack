package com.myweapon.hourglass.schedule.repository;


import com.myweapon.hourglass.schedule.dto.CalendarRemain;
import com.myweapon.hourglass.schedule.entity.Calender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calender,Long> {
    @Query("select new com.myweapon.hourglass.schedule.dto.CalendarRemain(c.description,c.dueDate) " +
            " from Calender c where c.user.id = :user_id and c.dueDate between :today and :after")
    public List<CalendarRemain> findRemainCalendar(@Param("user_id")Long user_id, @Param("today") LocalDate today, @Param("after")LocalDate after);
}
