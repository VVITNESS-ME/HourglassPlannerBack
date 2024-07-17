package com.myweapon.hourglass.statistics.entity;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Measurement(name="hourglassAudit")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HourglassAudit {
    public static final String START = "start";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String END = "end";

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

    public static HourglassAudit startOf(Hourglass hourglass, User user, LocalDateTime time){
        return of(hourglass,user,0,START,time);
    }
    public static HourglassAudit pauseOf(Hourglass hourglass, User user,Integer burstTime, LocalDateTime time){
        return of(hourglass,user,burstTime,PAUSE,time);
    }
    public static HourglassAudit resumeOf(Hourglass hourglass, User user, LocalDateTime time){
        return of(hourglass,user,0,RESUME,time);
    }

    public static HourglassAudit endOf(Hourglass hourglass, User user, Integer burstTime, LocalDateTime time){
        return of(hourglass,user,burstTime,END,time);
    }

    private static HourglassAudit of(Hourglass hourglass, User user, Integer burstTime, String type, LocalDateTime time){
        Instant timeConverted = time.atZone(ZoneId.of("Asia/Seoul")).toInstant();
        return HourglassAudit.builder()
                .hourglassId(hourglass.getId())
                .userId(user.getId())
                .burstTime(burstTime)
                .type(type)
                .time(timeConverted)
                .build();
    }
}
