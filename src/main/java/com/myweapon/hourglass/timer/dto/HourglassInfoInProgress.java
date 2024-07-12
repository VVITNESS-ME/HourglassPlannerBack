package com.myweapon.hourglass.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HourglassInfoInProgress {
    private Long hId;
    private Long taskId;
    private Integer timeGaol;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private LocalDateTime timePause;
    private LocalDateTime timeResume;
    private Integer timeBurst;
}
