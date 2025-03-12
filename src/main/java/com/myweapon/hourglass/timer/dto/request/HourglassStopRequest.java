package com.myweapon.hourglass.timer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class HourglassStopRequest {
    private LocalDateTime instant;
    private Integer burstTime;
}
