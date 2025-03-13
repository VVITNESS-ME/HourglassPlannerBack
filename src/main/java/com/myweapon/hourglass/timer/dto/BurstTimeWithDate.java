package com.myweapon.hourglass.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BurstTimeWithDate {
    private LocalDateTime localDateTime;
    private Integer burstTime;
}
