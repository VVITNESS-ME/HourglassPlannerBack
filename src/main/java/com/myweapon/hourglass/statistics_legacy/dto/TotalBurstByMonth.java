package com.myweapon.hourglass.statistics_legacy.dto;

import com.myweapon.hourglass.common.time.DateTimeFrameConstants;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TotalBurstByMonth {
    private final Integer month;
    private final Long totalBurst;
    public TotalBurstByMonth(LocalDateTime dateTime,Long totalBurst){
        this.month = dateTime.getMonthValue();
        this.totalBurst = totalBurst/ DateTimeFrameConstants.MINUTES;
    }
}
