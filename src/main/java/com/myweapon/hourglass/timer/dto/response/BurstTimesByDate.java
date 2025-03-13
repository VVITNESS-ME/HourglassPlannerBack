package com.myweapon.hourglass.timer.dto.response;

import com.myweapon.hourglass.timer.enumset.DateRangeType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BurstTimesByDate {
    private DateRangeType interval;
    private Map<LocalDateTime,Integer> burstTimesByDateTimeRange;

    public static BurstTimesByDate of(DateRangeType dateRangeType, Map<LocalDateTime,Integer> burstTimesByDateTimeRange){
        return BurstTimesByDate
                .builder()
                .interval(dateRangeType)
                .burstTimesByDateTimeRange(burstTimesByDateTimeRange)
                .build();
    }
}
