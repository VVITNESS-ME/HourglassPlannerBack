package com.myweapon.hourglass.statistics.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.entity.HourglassAudit;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HourglassAuditRepository {
    private final WriteApiBlocking writeApiBlocking;
    public void recordStartLog(Hourglass hourglass, User user){
        HourglassAudit hourglassAudit = HourglassAudit.startOf(hourglass,user);
//        System.out.println(hourglassAudit);
        writeApiBlocking.writeMeasurement(WritePrecision.S,hourglassAudit);
    }

    public void recordPauseLog(Hourglass hourglass, User user){
        List<HourglassAudit> hourglassAudits = HourglassAudit.pauseListOf(hourglass,user);
        writeApiBlocking.writeMeasurements(WritePrecision.S,hourglassAudits);
    }
    public void recordResumeLog(Hourglass hourglass, User user){
        HourglassAudit hourglassAudit = HourglassAudit.resumeOf(hourglass,user);
        writeApiBlocking.writeMeasurement(WritePrecision.S,hourglassAudit);
    }

    public void recordEndLog(Hourglass hourglass, User user){
//        System.out.println(hourglass.getEnd()+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        List<HourglassAudit> hourglassAudits = HourglassAudit.endListOf(hourglass,user);
        writeApiBlocking.writeMeasurements(WritePrecision.S, hourglassAudits);
    }
}
