package com.myweapon.hourglass.timer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class HourglassEndRequest {
    @JsonProperty("hId")
    private Long hId;
    private String timeStart;
    private Integer timeBurst;
    private String timeEnd;
}
