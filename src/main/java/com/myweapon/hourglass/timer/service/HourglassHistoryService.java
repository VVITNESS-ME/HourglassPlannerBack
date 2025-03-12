package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.db.DefaultHourglassHistoryInfo;
import com.myweapon.hourglass.timer.dto.request.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassRunRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStartRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStopRequest;
import com.myweapon.hourglass.timer.entity.Hourglass;

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
}
