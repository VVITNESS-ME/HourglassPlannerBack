package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.schedule.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface HourglassRepository extends JpaRepository<Hourglass,Long> {
    @Transactional
    @Modifying
    @Query("update Hourglass h set h.end = :end, h.burstTime = :burst, h.rating =:rating, h.content =:content, h.task =:task where h.id = :hId")
    public Integer updateToEnd(@Param("hId") Long hId, @Param("end") LocalDateTime end
            , @Param("burst") Integer burst, @Param("rating") Float rating, @Param("content") String content, @Param("task") Task task);

    @Transactional
    @Modifying
    @Query("update Hourglass h set h.content = :content where h.id = :hId")
    public Integer updateContent(@Param("hId") Long hId,@Param("content")String content);
}
