package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourglassResponse {
    private Long hid;

    public static HourglassResponse fromHourGlass(Hourglass hourglass){
        return HourglassResponse.builder()
                .hid(hourglass.getId())
                .build();
    }

    public static HourglassResponse fromHId(Long hid){
        return HourglassResponse.builder()
                .hid(hid)
                .build();
    }
}
