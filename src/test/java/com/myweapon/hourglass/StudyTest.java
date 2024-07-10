package com.myweapon.hourglass;

import com.myweapon.hourglass.common.time.DateTimeFrame;
import com.myweapon.hourglass.common.time.TimeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class StudyTest {
    @Test
    public void practice(){
        System.out.println("hello");
    }

    @Test
    public void timeTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeSent = "2024-07-02T13:52:48.196Z";
        String answer = timeSent.replace('T',' ').substring(0,timeSent.length()-5);
        System.out.println(answer);
        LocalDateTime localDateTime = LocalDateTime.parse(answer,formatter);
        System.out.println(localDateTime);
    }

    @Test
    public void durationTest(){
        LocalDate a = LocalDate.now();
        LocalDate b = a.plusDays(100);
        System.out.println(a.toString());

//        Duration d = Duration.between(a,b);
        Period d = Period.between(a,b);

        System.out.println(d.getDays());
    }

    @Test
    public void timeUtilsTest(){
        System.out.println(TimeUtils.inputFormatString("2024-07-02T13:52:48.196Z"));;
    }

    @Test
    public void dateTimeNow(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }
    @Test
    public void LocalDateTimeString(){
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().toString());
    }

    @Test
    public void monthTest(){
        String monthString = "2024-07-01";
        LocalDateTime yearStart = DateTimeFrame.FROM_DAY.toLocalDateTime(monthString);
//        System.out.println(yearStart.withDayOfYear(1));

//        LocalDateTime yearStart = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
//        LocalDate start = LocalDate.now().
//        System.out.println(yearStart);
    }
}
