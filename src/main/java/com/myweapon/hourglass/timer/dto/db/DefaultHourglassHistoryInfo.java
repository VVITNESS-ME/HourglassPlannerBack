package com.myweapon.hourglass.timer.dto.db;

import com.myweapon.hourglass.timer.enumset.HourglassState;
import com.myweapon.hourglass.timer.exception.TimerRestApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
