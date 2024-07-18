package com.myweapon.hourglass.statistics.service;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.myweapon.hourglass.config.InfluxDBConfig;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.response.DayStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.dto.response.MonthStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.YearStatisticsResponse;
import com.myweapon.hourglass.statistics.repository.HourglassAuditRepository;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InfluxStatisticsService implements StatisticsService{
    private final QueryApi queryApi;
    private final HourglassAuditRepository hourglassAuditRepository;

    public void recordStartLog(Hourglass hourglass, User user){
//        System.out.println(hourglass.getStart());
        hourglassAuditRepository.recordStartLog(hourglass,user);
    }

    public void recordPauseLog(Hourglass hourglass,User user){
        hourglassAuditRepository.recordPauseLog(hourglass,user);
    }

    public void recordResumeLog(Hourglass hourglass,User user){
        hourglassAuditRepository.recordResumeLog(hourglass,user);
    }

    public void recordEndLog(Hourglass hourglass, User user){
        hourglassAuditRepository.recordEndLog(hourglass,user);
    }

    @Override
    public GardenResponse getGardens(LocalDate start, LocalDate end, User user) {
        String startFormat = start.format(DateTimeFormatter.ISO_DATE);
        String endFormat = start.format(DateTimeFormatter.ISO_DATE);
        String flux = String.format("from(bucket:\"%s\") |> " +
                "range(start:-30h)|> " +
                "filter(fn: (r) => r.user_id == \"%s\")"
                , InfluxDBConfig.bucket,startFormat,endFormat, user.getId());
//        String flux = "from(bucket:\"s\") |> range(start:-30h)|>filter(fn: (r) => r.user_id == \"2\")";
        return null;
    }

    @Override
    public DayStatisticsResponse getDayStatistics(LocalDate date, User user) {
        return null;
    }

    @Override
    public MonthStatisticsResponse getMonthStatistics(LocalDate date, User user) {
        return null;
    }

    @Override
    public YearStatisticsResponse getYearStatistics(LocalDate date, User user) {
        return null;
    }
}
