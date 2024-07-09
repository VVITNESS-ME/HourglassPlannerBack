package com.myweapon.hourglass.statics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GardenGetResponse {
    private List<DayOfGarden> entries;
    public static GardenGetResponse of(List<DayOfGarden> entries){
        return GardenGetResponse.builder()
                .entries(entries)
                .build();
    }
}
