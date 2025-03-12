package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HourglassRepository extends JpaRepository<Hourglass,Long> {
    @Transactional
    @Modifying
    @Query("update Hourglass h set h.content = :content where h.id = :hId")
    public Integer updateContent(@Param("hId") Long hId,@Param("content")String content);

    /**
     * 현재 진행중인 모래시계들을 반환한다.
     * 끝나지 않은 모래시계가 있으면 다른 모래시계를 생성하지 못하므로 최대 1개만 나올 수 있다.
     */
    @Transactional
    @Query("select h from User u " +
            "join UserCategory uc on u = uc.user " +
            "join Task t on uc = t.userCategory " +
            "join Hourglass h on t=h.task " +
            "left join HourglassHistory hh on h = hh.hourglass and hh.state='end'" +
            "where hh is null and u.id=:userId")
    public Optional<Hourglass> getHourglassesInProgress(@Param("userId")Long userId);

}
