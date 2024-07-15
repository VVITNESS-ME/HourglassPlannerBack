package com.myweapon.hourglass.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GardenGetResponse {
    private List<TotalBurstByDay> entries;
    public static GardenGetResponse of(List<TotalBurstByDay> entries){
        return GardenGetResponse.builder()
                .entries(entries)
                .build();
    }
}
