package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.DateTimeRange;
import com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo;
import com.myweapon.hourglass.timer.dto.HourglassHistoryInfoWithCategory;
import com.myweapon.hourglass.timer.dto.request.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassRunRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStartRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStopRequest;
import com.myweapon.hourglass.timer.dto.response.BurstTimesByDate;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.enumset.DateRangeType;

import java.util.List;

/***
 * @see HourglassHistoryServiceImpl
 */
public interface HourglassHistoryService {
    public long recordStart(User user, Hourglass hourglass, HourglassStartRequest request);
    public long recordPause(User user,Hourglass hourglass, HourglassStopRequest request);
    public long recordRestart(User user,Hourglass hourglass, HourglassRunRequest request);
    public long recordEnd(User user,Hourglass hourglass, HourglassEndRequest request);
    public DefaultHourglassHistoryInfo findLastHourglassHistoryByHourglass(Hourglass hourglass);
    public List<DefaultHourglassHistoryInfo> findHourglassHistoriesBy(Hourglass hourglass);

    public List<HourglassHistoryInfoWithCategory> findHourglassHistoriesByUserAndDateTimeRange(User user, DateTimeRange dateTimeRange);

    public BurstTimesByDate sumBurstTimeByDate(User user, DateRangeType intervalType, DateTimeRange dateTimeRange);
}
