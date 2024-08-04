package com.myweapon.hourglass.timer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myweapon.hourglass.common.time.TimeUtils;
import com.myweapon.hourglass.timer.contants.HourglassConstant;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class HourglassEndRequest {

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
            return HourglassConstant.MID_RATING;
        }
        return rating;
    }
    public String getContent(){
        if(StringUtils.isEmpty(content)){
            return HourglassConstant.EMPTY;
        }
        return content;
    }
}
