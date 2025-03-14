package com.myweapon.hourglass.timer.service;


import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.BurstTimeWithDate;
import com.myweapon.hourglass.timer.dto.DateTimeRange;
import com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo;
import com.myweapon.hourglass.timer.dto.HourglassHistoryInfoWithCategory;
import com.myweapon.hourglass.timer.dto.request.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassRunRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStartRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStopRequest;
import com.myweapon.hourglass.timer.dto.response.BurstTimesByDate;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.HourglassHistory;
import com.myweapon.hourglass.timer.enumset.DateRangeType;
import com.myweapon.hourglass.timer.enumset.HourglassState;
import com.myweapon.hourglass.timer.exception.TimerRestApiException;
import com.myweapon.hourglass.timer.respository.HourglassHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HourglassHistoryServiceImpl implements HourglassHistoryService{
    private static final String LAST_HOURGLASS_STATE_PREFIX = "마지막 모래시계 상태 :";
    private final HourglassHistoryRepository hourglassHistoryRepository;

    @Override
    @Transactional
    public long recordStart(User user, Hourglass hourglass, HourglassStartRequest request) {
        Optional<DefaultHourglassHistoryInfo> hourglassHistoryInfo = hourglassHistoryRepository.findLastHourglassHistoryInfoByUserId(user.getId());
        if(hourglassHistoryInfo.isPresent()&&!request.getInstant().isAfter(hourglassHistoryInfo.get().getInstant())){
            throw new TimerRestApiException(TimerRestApiException.StartTimeMustLateThanBeforeHourglassHistory);
        }
        HourglassHistory hourglassHistory = HourglassHistory.ofStarted(user,hourglass,request);
        return save(hourglassHistory);
    }

    @Override
    @Transactional
    public long recordPause(User user,Hourglass hourglass, HourglassStopRequest request) {
        DefaultHourglassHistoryInfo lastHourglassHistoryInfo = findLastHourglassHistoryByHourglass(hourglass);
        checkHourglassNotEnd(lastHourglassHistoryInfo);
        checkNewHistoryIsLatest(request.getInstant(),lastHourglassHistoryInfo);
        if(lastHourglassHistoryInfo.getHourglassState().equals(HourglassState.PAUSE)){
            throw new TimerRestApiException(String.format("%s %s %s"
                    ,TimerRestApiException.CannotPause
                    ,LAST_HOURGLASS_STATE_PREFIX
                    ,lastHourglassHistoryInfo.getHourglassState().getState()));
        }

        HourglassHistory hourglassHistory = HourglassHistory.ofPaused(user,hourglass,request);
        return save(hourglassHistory);
    }

    @Override
    @Transactional
    public long recordRestart(User user,Hourglass hourglass, HourglassRunRequest request) {
        DefaultHourglassHistoryInfo lastHourglassHistoryInfo = findLastHourglassHistoryByHourglass(hourglass);
        checkHourglassNotEnd(lastHourglassHistoryInfo);
        checkNewHistoryIsLatest(request.getInstant(),lastHourglassHistoryInfo);
        if(lastHourglassHistoryInfo.getHourglassState().equals(HourglassState.START)
        ||lastHourglassHistoryInfo.getHourglassState().equals(HourglassState.RESTART)){
            throw new TimerRestApiException(String.format("%s %s %s"
                    ,TimerRestApiException.CannotRestart
                    ,LAST_HOURGLASS_STATE_PREFIX
                    ,lastHourglassHistoryInfo.getHourglassState().getState()));
        }

        HourglassHistory hourglassHistory = HourglassHistory.ofRestarted(user,hourglass,request);
        return save(hourglassHistory);
    }

    @Override
    @Transactional
    public long recordEnd(User user,Hourglass hourglass, HourglassEndRequest request) {
        DefaultHourglassHistoryInfo lastHourglassHistoryInfo = findLastHourglassHistoryByHourglass(hourglass);
        checkHourglassNotEnd(lastHourglassHistoryInfo);
        checkNewHistoryIsLatest(request.getInstant(),lastHourglassHistoryInfo);

        HourglassHistory hourglassHistory = HourglassHistory.ofEnd(user,hourglass,request);
        return save(hourglassHistory);
    }

    @Override
    public DefaultHourglassHistoryInfo findLastHourglassHistoryByHourglass(Hourglass hourglass) {
        return hourglassHistoryRepository.findLastHourglassHistoryBy(hourglass)
                .orElseThrow(()->new TimerRestApiException(TimerRestApiException.NoSuchHourglassHistory));
    }

    @Override
    public List<DefaultHourglassHistoryInfo> findHourglassHistoriesBy(Hourglass hourglass) {
        return hourglassHistoryRepository.findHistoriesBy(hourglass);
    }


    private void checkHourglassNotEnd(DefaultHourglassHistoryInfo info){
        if(info.getHourglassState().equals(HourglassState.END)){
            throw new TimerRestApiException(TimerRestApiException.HourglassIsEnd);
        }
    }

    private void checkNewHistoryIsLatest(LocalDateTime newHistoryInstant,DefaultHourglassHistoryInfo info){
        if(!newHistoryInstant.isAfter(info.getInstant())){
            throw new TimerRestApiException(TimerRestApiException.RequestTimeEarlier);
        }
    }

    private long save(HourglassHistory hourglassHistory){
        hourglassHistoryRepository.save(hourglassHistory);
        hourglassHistoryRepository.flush();//pk가 identitiy라 바로 반영되긴 하겠지만 나중에 바뀔 경우를 대비해서 남겨둠.
        return hourglassHistory.getId();
    }

    @Override
    public List<HourglassHistoryInfoWithCategory> findHourglassHistoriesByUserAndDateTimeRange(User user, DateTimeRange dateTimeRange){
        System.out.println(dateTimeRange.getStart());
        System.out.println(dateTimeRange.getEnd());
        return hourglassHistoryRepository
                .findStopHourglassHistoryWithCategoryBy(user,dateTimeRange.getStart(),dateTimeRange.getEnd())
                .stream()
                .map(HourglassHistoryInfoWithCategory::of)
                .toList();
    }

    @Override
    public BurstTimesByDate sumBurstTimeByDate(User user, DateRangeType dateRangeType, DateTimeRange dateTimeRange){
        List<BurstTimeWithDate> burstTimesWithDate = new ArrayList<>();
        if(dateRangeType.equals(DateRangeType.DAY)){
            burstTimesWithDate = hourglassHistoryRepository.sumHistoryByDay(user,dateTimeRange.getStart(),dateTimeRange.getEnd());
        }else if(dateRangeType.equals(DateRangeType.WEEK)){
            burstTimesWithDate = hourglassHistoryRepository.sumHistoryByWeek(user,dateTimeRange.getStart(),dateTimeRange.getEnd());
        }else if(dateRangeType.equals(DateRangeType.MONTH)){
            burstTimesWithDate = hourglassHistoryRepository.sumHistoryByMonth(user,dateTimeRange.getStart(),dateTimeRange.getEnd());
        }else{
            throw new TimerRestApiException(TimerRestApiException.NoSuchTask);
        }

        Map<LocalDateTime,Integer> burstTimesByDate = burstTimesWithDate
                .stream()
                .collect(Collectors.toMap(BurstTimeWithDate::getLocalDateTime,BurstTimeWithDate::getBurstTime));

        return BurstTimesByDate.of(dateRangeType,burstTimesByDate);
    }
}
