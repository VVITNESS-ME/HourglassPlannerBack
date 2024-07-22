package com.myweapon.hourglass.statistics.dto.field;

import com.influxdb.query.FluxRecord;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Builder
public record BurstTimeByMonth(Integer month, Integer totalBurst) {
    public static BurstTimeByMonth of(FluxRecord fluxRecord){
        return BurstTimeByMonth.builder()
                .month(LocalDateTime.ofInstant(fluxRecord.getTime(), ZoneOffset.UTC).getMonthValue())
                .totalBurst(((Long)fluxRecord.getValue()).intValue())
                .build();
    }

    public static BurstTimeByMonth emptyOf(int month){
        return BurstTimeByMonth.builder()
                .month(month)
                .totalBurst(0)
                .build();
    }
}
