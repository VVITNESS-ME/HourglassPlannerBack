package com.myweapon.hourglass.statistics.entity;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Measurement(name="hourglassAudit")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class HourglassAudit {
    public static final String START = "start";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String END = "end";
    public static final String CONTINUE = "continue";

    @Column(tag = true)
    Long userId;

    @Column(tag = true)
    Long hourglassId;

    @Column
    Integer burstTime;

    @Column
    String type;

    @Column(timestamp = true)
    Instant time;

    public static HourglassAudit startOf(Hourglass hourglass, User user){
        return of(hourglass,user,0,START,hourglass.getStart());
    }

    public static List<HourglassAudit> pauseListOf(Hourglass hourglass, User user){
        LocalDateTime last;
        if(hourglass.getLast_resume()==null){
            last = hourglass.getStart();
        }
        else{
            last = hourglass.getLast_resume();
        }

        return splitHourglassAuditByDay(hourglass,user,last);
    }
    public static HourglassAudit resumeOf(Hourglass hourglass, User user){
        return of(hourglass,user,0,RESUME,hourglass.getLast_resume());
    }

    public static List<HourglassAudit> endListOf(Hourglass hourglass, User user){
        LocalDateTime last;
        if(hourglass.getLast_resume()==null){
            last = hourglass.getStart();
        }
        else{
            last = hourglass.getLast_resume();
        }

        return splitHourglassAuditByDay(hourglass,user,last);
    }

    private static List<HourglassAudit> splitHourglassAuditByDay(Hourglass hourglass,User user,LocalDateTime last){
        LocalDateTime end = hourglass.getEnd();
        List<HourglassAudit> audits = new ArrayList<>();

        if(last.isAfter(end)){
            throw new RestApiException(ErrorType.DUPLICATED_NAME);
        }

        while(!last.toLocalDate().equals(end.toLocalDate())){
            LocalDateTime lastOfDateFromLast = last.withHour(23).withMinute(59).withSecond(59);
            Integer gap = (int)Duration.between(last,lastOfDateFromLast).toSeconds();
            HourglassAudit hourglassAuditForContinue = of(hourglass,user,gap,CONTINUE,last);
            audits.add(hourglassAuditForContinue);
            last = lastOfDateFromLast.plusSeconds(1);
        }

        Duration duration = Duration.between(last,hourglass.getEnd());
        Integer burstTime =(int)duration.toSeconds();

        audits.add(of(hourglass,user,burstTime,END,hourglass.getEnd()));

        return audits;
    }


    private static HourglassAudit of(Hourglass hourglass, User user, Integer burstTime, String type, LocalDateTime time){
        Instant timeConverted = time.toInstant(ZoneOffset.UTC);
        return HourglassAudit.builder()
                .hourglassId(hourglass.getId())
                .userId(user.getId())
                .burstTime(burstTime)
                .type(type)
                .time(timeConverted)
                .build();
    }
}
