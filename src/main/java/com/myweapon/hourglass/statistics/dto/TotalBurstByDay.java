package com.myweapon.hourglass.statistics.dto;

import com.myweapon.hourglass.common.time.DateTimeFrameConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TotalBurstByDay {
    private LocalDate date;
    private Long totalBurst;
    public TotalBurstByDay(LocalDateTime dateString,Long totalBurst){
        this.date = dateString.toLocalDate();
        this.totalBurst = totalBurst/ DateTimeFrameConstants.MINUTES;
    }

    public Boolean isDate(LocalDate date){
        return this.date.equals(date);
    }
}
