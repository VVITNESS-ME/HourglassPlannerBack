package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourglassResponse {
    private Long hId;

    public static HourglassResponse fromHourGlass(Hourglass hourglass){
        return HourglassResponse.builder()
                .hId(hourglass.getId())
                .build();
    }

    public static HourglassResponse fromHId(Long hid){
        return HourglassResponse.builder()
                .hId(hid)
                .build();
    }
}
