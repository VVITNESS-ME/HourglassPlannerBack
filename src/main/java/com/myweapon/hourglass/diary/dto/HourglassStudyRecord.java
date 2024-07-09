package com.myweapon.hourglass.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class HourglassStudyRecord {
    private Long hId;
    private String categoryName;
    private String color;
    private String taskName;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Integer timeBurst;
    private Float rating;

    public HourglassStudyRecord(Long hId,String categoryName
            ,String color,String taskName,LocalDateTime timeStart,LocalDateTime timeEnd,Integer timeBurst,Float rating){
        this.hId = hId;
        this.categoryName = categoryName;
        this.color = color;
        this.taskName = taskName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.timeBurst = timeBurst;
        this.rating = rating;
    }
}
