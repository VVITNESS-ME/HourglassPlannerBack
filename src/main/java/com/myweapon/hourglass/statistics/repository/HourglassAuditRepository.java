package com.myweapon.hourglass.statistics.repository;

import com.influxdb.client.InfluxDBClient;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HourglassAuditRepository {
    private final InfluxDBClient influxDBClient;
    public void recordLog(Hourglass hourglass, User user){

    }
}
