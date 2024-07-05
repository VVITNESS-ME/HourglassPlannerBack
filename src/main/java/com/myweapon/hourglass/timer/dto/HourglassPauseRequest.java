package com.myweapon.hourglass.timer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HourglassPauseRequest {
    @JsonProperty("hId")
    private Long hId;
    private String timeStart;
    private String timeGaol;
    private Integer timeBurst;
}