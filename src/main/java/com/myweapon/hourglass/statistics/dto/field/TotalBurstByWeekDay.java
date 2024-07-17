package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;

@Builder
public record TotalBurstByWeekDay(String weekDay,Integer totalBurst) {
}
