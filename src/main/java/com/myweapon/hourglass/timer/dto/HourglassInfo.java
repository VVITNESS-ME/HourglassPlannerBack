package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HourglassInfo {
    private Long hourglassId;
    private List<DefaultHourglassHistoryInfo> histories;

    public static HourglassInfo of(Hourglass hourglass,List<DefaultHourglassHistoryInfo> histories){
        return HourglassInfo
                .builder()
                .hourglassId(hourglass.getId())
                .histories(histories)
                .build();
    }
}
