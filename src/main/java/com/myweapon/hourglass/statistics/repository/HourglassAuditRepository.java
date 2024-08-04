package com.myweapon.hourglass.statistics.repository;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxTable;
import com.myweapon.hourglass.config.InfluxDBConfig;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByDay;
import com.myweapon.hourglass.statistics.dto.field.BurstTimeByMonth;
import com.myweapon.hourglass.statistics.entity.HourglassAudit;
import com.myweapon.hourglass.timer.entity.Hourglass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class HourglassAuditRepository {
    private final WriteApiBlocking writeApiBlocking;
    private final QueryApi queryApi;
    @Value("${influx.bucket}")
    private String bucket;
    public void recordStartLog(Hourglass hourglass, User user){
        HourglassAudit hourglassAudit = HourglassAudit.startOf(hourglass,user);
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
        List<HourglassAudit> hourglassAudits = HourglassAudit.endListOf(hourglass,user);
        writeApiBlocking.writeMeasurements(WritePrecision.S, hourglassAudits);
    }

    /**
     * start에서 end의 기간 동안 각 하루의 총 공부량을 계산.
     * end는 공부하는 날짜에 포함시키지 않는다.
     * 예를들어 7월의 기간(07-01 ~ 07-31)의 통계량을 알고 싶다면
     * start는 07-01, end는 08-01의 날짜로 요청해야 한다.
     * @param start
     * @param end
     * @param user
     * @return
     */
    public List<BurstTimeByDay> getBurstTimeByDay(LocalDate start, LocalDate end, User user) {
        String startIsoInstanceString = localDateToIsoInstantString(start);
        String endIsoInstanceString = localDateToIsoInstantString(end);
        String flux = String.format("import \"experimental\" "+
                        "from(bucket:\"%s\") " +
                        "|> range(start:%s, stop:%s) " +
                        "|> filter(fn: (r) => r[\"_measurement\"] == \"hourglassAudit\") " +
                        "|> filter(fn: (r) => r.userId == \"%s\") " +
                        "|> drop(columns: [\"hourglassId\"]) " +
                        "|> filter(fn: (r) => r[\"_field\"] == \"burstTime\") " +
                        "|> aggregateWindow(every: 1d, fn: sum, createEmpty: true)"+
//<<<<<<< HEAD
                        "|> fill(value:0) " +
                        "|> map(fn: (r) => ({ r with _time: experimental.addDuration(d: -1d, to:r._time) })) "
                , bucket, startIsoInstanceString, endIsoInstanceString, user.getId());
//=======
//                        "|> fill(value:0)"
//                , bucket, startIsoInstanceString, endIsoInstanceString, user.getId());
//>>>>>>> 86347c3c8d3ecdd0a6f8c8400a926ba8607308cb
        List<FluxTable> tables = queryApi.query(flux);

        if (tables.isEmpty()){
            return BurstTimeByDay.zeroBurstTimeListFrom(start,end);
        }
        return BurstTimeByDay.listFrom(tables.get(0).getRecords());
    }

    public Optional<FluxTable> getDayRecords(LocalDate date, User user){
        String dateIsoInstanceString = localDateToIsoInstantString(date);
        String nextDateStartIsoInstanceString  = localDateToIsoInstantString(date.plusDays(1));

        String flux = String.format("from(bucket:\"%s\") " +
                        "|> range(start:%s, stop:%s) " +
                        "|> filter(fn: (r) => r[\"_measurement\"] == \"hourglassAudit\")" +
                        "|> filter(fn: (r) => r.userId == \"%s\") " +
                        "|> drop(columns: [\"hourglassId\"]) " +
                        "|> filter(fn: (r) => r._field == \"burstTime\") " +
                        "|> filter(fn: (r) => r._value != 0)"+
                        "|> keep(columns: [\"_time\", \"_value\"])"
                , bucket, dateIsoInstanceString, nextDateStartIsoInstanceString, user.getId());
        List<FluxTable> tables = queryApi.query(flux);

        if (tables.isEmpty()){
            return Optional.<FluxTable>empty();
        }
        return Optional.of(tables.get(0));
    }

    public List<BurstTimeByMonth> getBurstTimeByMonth(LocalDate date,User user){
        String startOfYear = localDateToIsoInstantString(date.withDayOfYear(1));
        String endOfYear = localDateToIsoInstantString(date.withDayOfYear(1).plusYears(1));

        String flux = String.format("import \"experimental\" "+
                "from(bucket: \"%s\") " +
                "|> range(start: %s, stop: %s) " +
                "|> filter(fn: (r) => r[\"_measurement\"] == \"hourglassAudit\") " +
                "|> filter(fn: (r) => r.userId == \"%s\") " +
                "|> drop(columns: [\"hourglassId\"]) " +
                "|> filter(fn: (r) => r._field == \"burstTime\") " +
                "|> aggregateWindow(every: 1mo, fn: sum, createEmpty: true) " +
                "|> map(fn: (r) => ({ r with _time: experimental.addDuration(d: -1mo, to:r._time) })) " +
                "|> fill(column: \"_value\", value: 0)"
                ,bucket, startOfYear,endOfYear,user.getId());

        List<FluxTable> tables = queryApi.query(flux);
        if (tables.isEmpty()){
            return Stream.iterate(1,e->e+1)
                    .limit(12)
                    .map(BurstTimeByMonth::emptyOf)
                    .toList();
        }
        return tables.get(0).getRecords().stream()
                .map(BurstTimeByMonth::of)
                .toList();
    }

    private String localDateToIsoInstantString(LocalDate localDate){
        return localDate
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC)
                .toString();
    }
}