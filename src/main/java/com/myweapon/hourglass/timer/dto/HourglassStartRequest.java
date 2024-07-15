package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.common.time.TimeUtils;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourglassStartRequest {
    private String timeStart;
    private Integer timeGoal;

    public LocalDateTime getTimeStart(){
        return TimeUtils.inputFormatString(timeStart);
    }
}
