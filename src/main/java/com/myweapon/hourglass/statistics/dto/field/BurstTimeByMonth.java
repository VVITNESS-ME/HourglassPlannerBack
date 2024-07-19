package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;

@Builder
public record BurstTimeByMonth(Integer month, Integer totalBurst) {
    public static BurstTimeByMonth of(BurstTimeByDay burstTimeByDay){
        return BurstTimeByMonth.builder()
                .month(burstTimeByDay.day().getMonthValue())
                .totalBurst(burstTimeByDay.burstTime())
                .build();
    }
}
