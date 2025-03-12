package com.myweapon.hourglass.timer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class HourglassRunRequest {
    LocalDateTime instant;

}
