package com.myweapon.hourglass.diary.repository;

import com.myweapon.hourglass.diary.dto.HourglassStudyRecord;
import com.myweapon.hourglass.diary.entity.StudyStaticsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudyStaticsViewRepository extends JpaRepository<StudyStaticsView,Long> {

    @Query("select new com.myweapon.hourglass.diary.dto.HourglassStudyRecord(s.hourglassId,s.categoryName,s.color " +
            ",s.taskTitle,s.start,s.end,s.burstTime,s.rating) from StudyStaticsView s " +
            "where s.userId = :userId and s.end between :start and :end")
    public List<HourglassStudyRecord> findHourglassStudyRecords(@Param("start") LocalDateTime start, @Param("end")LocalDateTime end, @Param("userId") Long userId);
}


