package com.myweapon.hourglass.statics.repository;

import com.myweapon.hourglass.diary.dto.HourglassStudyRecord;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statics.dto.DayOfGarden;
import com.myweapon.hourglass.statics.entity.StudyStaticsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudyStaticsViewRepository extends JpaRepository<StudyStaticsView,Long> {

    @Query("select new com.myweapon.hourglass.diary.dto.HourglassStudyRecord(s.hourglassId,s.categoryName,s.color " +
            ",s.taskTitle,s.start,s.end,s.burstTime,s.rating,s.hourglassContent) from StudyStaticsView s " +
            "where s.userId = :userId and s.end between :start and :end")
    public List<HourglassStudyRecord> findHourglassStudyRecords(@Param("start") LocalDateTime start, @Param("end")LocalDateTime end, @Param("userId") Long userId);

    @Query("select new com.myweapon.hourglass.statics.dto.DayOfGarden(s.end,sum(s.burstTime))" +
            "from StudyStaticsView s " +
            "where s.userId = :userId and s.end between :start and :end " +
            "group by function('date',s.end)")
    public List<DayOfGarden> findGardenOfInterval(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,@Param("userId") Long userId);
}


