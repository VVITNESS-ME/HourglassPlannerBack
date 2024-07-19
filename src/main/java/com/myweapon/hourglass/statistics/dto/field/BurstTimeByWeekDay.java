package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;

@Builder
public record BurstTimeByWeekDay(String weekDay, Integer totalBurst) {
}
