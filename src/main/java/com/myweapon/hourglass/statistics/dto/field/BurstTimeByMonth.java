package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;

@Builder
public record BurstTimeByMonth(Integer month, Integer totalBurst) {
}
