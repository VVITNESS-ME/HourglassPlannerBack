package com.myweapon.hourglass.timer.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourglassStartRequest {
    private String timeStart;
    private Integer timeGoal;
}
