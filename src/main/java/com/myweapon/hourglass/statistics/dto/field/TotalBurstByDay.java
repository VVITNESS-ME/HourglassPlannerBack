package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TotalBurstByDay(LocalDate day, Integer totalBurst) {
}
