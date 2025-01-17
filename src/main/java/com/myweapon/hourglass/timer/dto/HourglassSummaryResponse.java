package com.myweapon.hourglass.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourglassSummaryResponse {
    private Long hId;
    private List<StudySummeryWithCategoryName> todaySummery;

    public static HourglassSummaryResponse of(Long hid,List<StudySummeryWithCategoryName> todaySummery){
        return HourglassSummaryResponse.builder()
                .hId(hid)
                .todaySummery(todaySummery)
                .build();
    }
}
