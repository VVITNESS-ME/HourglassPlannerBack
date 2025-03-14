package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.common.time.DateTimeUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@NoArgsConstructor
public class BurstTimeWithDate {
    private LocalDateTime localDateTime;
    private Integer burstTime;

    public BurstTimeWithDate (LocalDateTime localDateTime,Long burstTime){
        this.localDateTime = localDateTime;
        this.burstTime = burstTime.intValue();
    }

    public BurstTimeWithDate(Object dateTimeValue, Long burstTime){
        if(dateTimeValue instanceof String){
            this.localDateTime = DateTimeUtils.stringToLocalDate((String) dateTimeValue).atStartOfDay();
        }else if(dateTimeValue instanceof Date){
            this.localDateTime = ((Date)dateTimeValue).toLocalDate().atStartOfDay();
        }else{
            throw new RuntimeException("알수 없는 타입으로 객체를 생성할 수 없습니다.");
        }
        this.burstTime = burstTime.intValue();
    }
}
