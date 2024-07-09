package com.myweapon.hourglass.diary.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class DayStudyRecord {
    List<HourglassStudyRecord> records;

    @Builder(access = AccessLevel.PRIVATE)
    private DayStudyRecord(List<HourglassStudyRecord> records){
        this.records = records;
    }

    public static DayStudyRecord from(List<HourglassStudyRecord> records){
        return DayStudyRecord.builder()
                .records(records)
                .build();
    }

}
