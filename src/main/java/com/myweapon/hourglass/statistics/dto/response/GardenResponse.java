package com.myweapon.hourglass.statistics.dto.response;

import com.myweapon.hourglass.statistics.dto.field.BurstTimeByDay;
import lombok.Builder;

import java.util.List;

@Builder
public record GardenResponse(List<BurstTimeByDay> entries) {
    public static GardenResponse of(List<BurstTimeByDay> entries){
        return GardenResponse.builder()
                .entries(entries)
                .build();
    }
}
