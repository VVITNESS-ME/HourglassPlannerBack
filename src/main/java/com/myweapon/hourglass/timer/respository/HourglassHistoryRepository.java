package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.BurstTimeWithDate;
import com.myweapon.hourglass.timer.dto.HourglassHistoryWithCategory;
import com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.HourglassHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HourglassHistoryRepository extends JpaRepository<HourglassHistory,Long> {

    @Query("select new com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo(h.id,h.instant,h.state,h.burstSecond) " +
            "from HourglassHistory h " +
            "where h.hourglass =:hourglass " +
            "order by h.instant desc " +
            "limit 1")
    public Optional<DefaultHourglassHistoryInfo> findLastHourglassHistoryBy(@Param("hourglass") Hourglass hourglass);

    @Query("select new com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo(h.id,h.instant,h.state,h.burstSecond) " +
            "from HourglassHistory h " +
            "where h.user.id = :userId " +
            "order by h.instant desc " +
            "limit 1")
    public Optional<DefaultHourglassHistoryInfo> findLastHourglassHistoryInfoByUserId(@Param("userId") Long UserId);

    @Query("select new com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo(h.id,h.instant,h.state,h.burstSecond) " +
            "from HourglassHistory h " +
            "where h.hourglass = :hourglass ")
    public List<DefaultHourglassHistoryInfo>  findHistoriesBy(@Param("hourglass") Hourglass hourglass);

    @Query("select new com.myweapon.hourglass.timer.dto.HourglassHistoryWithCategory(hh,uc.id,c.name,uc.color) " +
            "from HourglassHistory hh " +
            "join Hourglass h on hh.hourglass = h " +
            "join Task t on h.task = t " +
            "join UserCategory uc on t.userCategory = uc " +
            "join Category c on uc.category = c " +
            "where hh.user = :user " +
            "and hh.instant between :s and :e " +
            "and hh.state='PAUSE' or hh.state = 'END'")
    public List<HourglassHistoryWithCategory> findStopHourglassHistoryWithCategoryBy(@Param("user") User user, @Param("s") LocalDateTime start, @Param("e") LocalDateTime end);

    @Query("select new com.myweapon.hourglass.timer.dto.BurstTimeWithDate(" +
            "function('date_format',h.instant,'%Y-%m-%d'), sum(h.burstSecond)) from HourglassHistory h " +
            "where h.user = :user and h.instant between :s and :e " +
            "group by function('date_format',h.instant,'%Y-%m-%d')")
    public List<BurstTimeWithDate> sumHistoryByDay(@Param("user") User user, @Param("s") LocalDateTime start, @Param("e") LocalDateTime end);
    @Query("select new com.myweapon.hourglass.timer.dto.BurstTimeWithDate(" +
            "function('str_to_date'" +
            ",function('concat',function('yearweek',function('any_value',h.instant)),'Sunday'),'%X%V%W'), sum(h.burstSecond)) from HourglassHistory h " +
            "where h.user = :user and h.instant between :s and :e " +
            "group by function('yearweek',h.instant)")
    public List<BurstTimeWithDate> sumHistoryByWeek(@Param("user") User user, @Param("s") LocalDateTime start, @Param("e") LocalDateTime end);

    @Query("select new com.myweapon.hourglass.timer.dto.BurstTimeWithDate(" +
            "function('concat',function('date_format',function('any_value',h.instant),'%Y-%m'),'-01'), sum(h.burstSecond)) from HourglassHistory h " +
            "where h.user = :user and h.instant between :s and :e " +
            "group by function('date_format',h.instant,'%Y-%m')")
    public List<BurstTimeWithDate> sumHistoryByMonth(@Param("user") User user, @Param("s") LocalDateTime start, @Param("e") LocalDateTime end);
}
