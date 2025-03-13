package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.entity.HourglassHistory;
import com.myweapon.hourglass.timer.enumset.HourglassState;
import com.myweapon.hourglass.timer.exception.TimerRestApiException;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DefaultHourglassHistoryInfo {
    Long id;
    LocalDateTime instant;
    HourglassState hourglassState;
    Integer burstTime;

    public DefaultHourglassHistoryInfo(Long id,LocalDateTime instant,String hourglassState,Integer burstTime){
        this.id = id;
        this.instant = instant;
        this.hourglassState = HourglassState
                .getHourglassState(hourglassState)
                .orElseThrow(()-> TimerRestApiException.of(TimerRestApiException.NoSuchHourglassState));
        this.burstTime = burstTime;
    }
    public static DefaultHourglassHistoryInfo of(HourglassHistory hourglassHistory){
        return DefaultHourglassHistoryInfo
                .builder()
                .hourglassState(hourglassHistory.getState())
                .burstTime(hourglassHistory.getBurstSecond())
                .id(hourglassHistory.getId())
                .instant(hourglassHistory.getInstant())
                .build();
    }

}
