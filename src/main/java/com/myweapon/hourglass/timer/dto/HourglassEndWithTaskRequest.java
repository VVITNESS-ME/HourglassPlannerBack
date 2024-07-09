package com.myweapon.hourglass.timer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myweapon.hourglass.common.TimeUtils;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class HourglassEndWithTaskRequest {
    private final Float MID_RATING = 2.5f;
    private final String EMPTY = "비어 있음";

    private Integer timeBurst;
    private String timeEnd;
    @JsonProperty("hId")
    private Long hId;
    private Float rating;
    private String description;

    public LocalDateTime getTimeEnd(){
        return TimeUtils.inputFormatString(timeEnd);
    }

    public Float getRating(){
        if(rating==null){
            return MID_RATING;
        }
        return rating;
    }
    public String getContent(){
        if(StringUtils.isEmpty(description)){
            return EMPTY;
        }
        return description;
    }
}
