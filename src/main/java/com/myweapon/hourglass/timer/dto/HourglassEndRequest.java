package com.myweapon.hourglass.timer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myweapon.hourglass.common.TimeUtils;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@NoArgsConstructor

@Getter
@Setter
public class HourglassEndRequest {
    private final static Float MID_RATING = 2.5f;
    private final static String EMPTY = "비어 있음";

    @JsonProperty("hId")
    private Long hId;
    private String timeStart;
    private Integer timeBurst;
    private String timeEnd;
    private String categoryName;
    private Float rating;
    private String content;

    public LocalDateTime getTimeEnd(){
        return TimeUtils.inputFormatString(timeEnd);
    }

    public String getCategoryName(){
        if(categoryName==null){
            return DefaultCategory.OTHERS.getName();
        }
        return categoryName;
    }

    public Float getRating(){
        if(rating==null){
            return MID_RATING;
        }
        return rating;
    }
    public String getContent(){
        if(StringUtils.isEmpty(content)){
            return EMPTY;
        }
        return content;
    }
}
