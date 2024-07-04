package com.myweapon.hourglass;

import com.myweapon.hourglass.common.TimeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public void timeUtilsTest(){
        System.out.println(TimeUtils.formatString("2024-07-02T13:52:48.196Z"));;
    }

    @Test
    public void dateTimeNow(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }
}
