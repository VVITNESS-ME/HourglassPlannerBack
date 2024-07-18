package com.myweapon.hourglass.statistics.dto.field;

import com.influxdb.query.FluxRecord;
import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public record BurstTimeByDay(LocalDate day, Integer totalBurst) {
    public static List<BurstTimeByDay> zeroBurstTimeListFrom(LocalDate start,LocalDate end){
        if(end.isBefore(start)){
            throw new RestApiException(ErrorType.TIME_ORDER_ERROR);
        }
        List<BurstTimeByDay> burstTimeByDays = new ArrayList<>();
        while(!start.equals(end)){
            burstTimeByDays.add(of(start,0));
            start = start.plusDays(1);
        }
        return burstTimeByDays;
    }

    public static BurstTimeByDay from(FluxRecord fluxRecord){
        return of(LocalDate.ofInstant
                (Objects.requireNonNull(fluxRecord.getTime()), ZoneOffset.UTC)
                ,((Long)fluxRecord.getValue()).intValue());
    }

    public static List<BurstTimeByDay> listFrom(List<FluxRecord> fluxRecords){
        List<BurstTimeByDay> list = new ArrayList<>();
        for(FluxRecord fluxRecord: fluxRecords){
            list.add(from(fluxRecord));
        }
        return list;
    }

    public static BurstTimeByDay of(LocalDate day, Integer totalBurst){
        return BurstTimeByDay.builder().day(day).totalBurst(totalBurst).build();
    }
}
