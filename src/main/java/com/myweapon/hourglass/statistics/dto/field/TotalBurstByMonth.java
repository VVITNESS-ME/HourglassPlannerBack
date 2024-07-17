package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;

@Builder
public record TotalBurstByMonth(Integer month, Integer totalBurst) {
}
