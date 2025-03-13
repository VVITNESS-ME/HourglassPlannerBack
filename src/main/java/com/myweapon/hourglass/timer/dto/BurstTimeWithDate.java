package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.dto.response.BurstTimesByDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BurstTimeWithDate {
    private LocalDateTime localDateTime;
    private Integer burstTime;

    public BurstTimeWithDate (LocalDateTime localDateTime,Long burstTime){
        this.localDateTime = localDateTime;
        this.burstTime = burstTime.intValue();
    }

    public BurstTimeWithDate(Object o,Long burstTime){
        this.localDateTime = (LocalDateTime)o ;
        this.burstTime = burstTime.intValue();
    }
}
