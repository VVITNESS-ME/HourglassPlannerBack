package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.dto.HourglassInfoInProgress;
import com.myweapon.hourglass.timer.dto.StudySummeryWithCategory;
import com.myweapon.hourglass.timer.entity.UserHourglass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserHourglassRepository extends JpaRepository<UserHourglass,Long> {

    @Query("select uh from UserHourglass uh where uh.user_id=:id")
    public List<UserHourglass> findUserHourglassByUserId(@Param("id") Long id);

    @Query("select uh from UserHourglass uh where uh.user_id=:id and uh.end is null")
    public Optional<UserHourglass> findUserHourglassNotEndByUserId(@Param("id") Long id);
//    private Long hId;
//    private Long taskId;
//    private Integer timeGaol;
//    private LocalDateTime timeStart;
//    private LocalDateTime timeEnd;
//    private LocalDateTime timePause;
//    private LocalDateTime timeResume;
//    private Integer timeBurst;
    @Query("select new com.myweapon.hourglass.timer.dto.HourglassInfoInProgress(uh.id,uh.task_id,uh.goal,uh.start,uh.end,uh.last_pause,uh.last_resume,uh.burst_time) " +
            "from UserHourglass uh where uh.user_id =:user_id and uh.end is null")
    public Optional<HourglassInfoInProgress> findHourglassInProgress(@Param("user_id")Long userId);

    @Query("select new com.myweapon.hourglass.timer.dto.StudySummeryWithCategory(uh.user_category_id,uh.start,uh.end,uh.burst_time,uh.color) " +
            "from UserHourglass uh where uh.user_id = :userId and uh.end between :start and :end")
    public List<StudySummeryWithCategory> studySummeryByDay(@Param("userId") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
