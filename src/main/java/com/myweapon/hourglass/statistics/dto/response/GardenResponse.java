package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.TotalBurstByDay;
import lombok.Builder;

import java.util.List;

@Builder
public record GardenResponse(List<TotalBurstByDay> entries) {
}
