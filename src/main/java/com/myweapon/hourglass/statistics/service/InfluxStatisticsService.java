package com.myweapon.hourglass.statistics.service;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.myweapon.hourglass.config.InfluxDBConfig;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByDay;
import com.myweapon.hourglass.statistics.dto.response.DayStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.dto.response.MonthStatisticsResponse;
import com.myweapon.hourglass.statistics.dto.response.YearStatisticsResponse;
import com.myweapon.hourglass.statistics.repository.HourglassAuditRepository;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    public List<Integer> getBurstTimeByHour(LocalDate date, User user){
        Optional<FluxTable> optionalDayRecords = hourglassAuditRepository.getDayRecords(date,user);
        List<Integer> burstTimeByHourDuringDay = burstTimeByHourDuringDay();
        if(optionalDayRecords.isEmpty()){
            return burstTimeByHourDuringDay;
        }

        FluxTable dayRecords = optionalDayRecords.get();

        for(FluxRecord hourglassRecord : dayRecords.getRecords()){
            Integer burstTime = ((Long) Objects.requireNonNull(hourglassRecord.getValue())).intValue();
            LocalTime timeInstant = LocalDateTime.ofInstant(hourglassRecord.getTime(), ZoneOffset.UTC).toLocalTime();

            int secondAfterHour = timeInstant.getSecond()+timeInstant.getMinute()*60;

            if(secondAfterHour >= burstTime){
                int hour = timeInstant.getHour();
                int before = burstTimeByHourDuringDay.get(hour);
                burstTimeByHourDuringDay.set(hour,before+burstTime);
                continue;
            }

            int hour = timeInstant.getHour();
            int before = burstTimeByHourDuringDay.get(hour);
            burstTimeByHourDuringDay.set(hour,before+secondAfterHour);
            hour --;
            burstTime -= secondAfterHour;

            while(burstTime > 3600){
                before = burstTimeByHourDuringDay.get(hour);
                burstTimeByHourDuringDay.set(hour, before+3600);
                hour--;
                burstTime-=3600;
            }

            before = burstTimeByHourDuringDay.get(hour);
            burstTimeByHourDuringDay.set(hour,before+burstTime);
        }

        return burstTimeByHourDuringDay;
    }

    @Override
    public GardenResponse getGardens(LocalDate start, LocalDate end, User user) {
        List<BurstTimeByDay> tables =  hourglassAuditRepository.getBurstTimeByDay(start,end,user);
        return GardenResponse.of(tables);
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

    private List<Integer> burstTimeByHourDuringDay(){
        return new ArrayList<>(Collections.nCopies(24,0));
    }
}
