package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.dto.db.DefaultHourglassHistoryInfo;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.HourglassHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface HourglassHistoryRepository extends JpaRepository<HourglassHistory,Long> {

    @Query("select new com.myweapon.hourglass.timer.dto.db.DefaultHourglassHistoryInfo(h.id,h.instant,h.state,h.burstSecond) " +
            "from HourglassHistory h " +
            "where h.hourglass =:hourglass " +
            "order by h.instant desc " +
            "limit 1")
    public Optional<DefaultHourglassHistoryInfo> findLastHourglassHistoryBy(@Param("hourglass") Hourglass hourglass);

    @Query("select new com.myweapon.hourglass.timer.dto.db.DefaultHourglassHistoryInfo(h.id,h.instant,h.state,h.burstSecond) " +
            "from HourglassHistory h " +
            "where h.user.id = :userId " +
            "order by h.instant desc " +
            "limit 1")
    public Optional<DefaultHourglassHistoryInfo> findLastHourglassHistoryInfoByUserId(@Param("userId") Long UserId);

    @Query("select new com.myweapon.hourglass.timer.dto.db.DefaultHourglassHistoryInfo(h.id,h.instant,h.state,h.burstSecond) " +
            "from HourglassHistory h " +
            "where h.hourglass = :hourglass ")
    public List<DefaultHourglassHistoryInfo>  findHistoriesBy(@Param("hourglass") Hourglass hourglass);

}
