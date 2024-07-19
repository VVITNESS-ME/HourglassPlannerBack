package com.myweapon.hourglass.common.time;

import java.time.LocalDate;
import java.util.function.Function;

public enum Week {
    MONDAY(1,"월",(date)->findWeekInterval(date,0)),
    TUESDAY(2,"화",(date)->findWeekInterval(date,1)),
    WEDNESDAY(3,"수",(date)->findWeekInterval(date,2)),
    THURSDAY(4,"목",(date)->findWeekInterval(date,3)),
    FRIDAY(5,"금",(date)->findWeekInterval(date,4)),
    SATURDAY(6,"토",(date)->findWeekInterval(date,5)),
    SUNDAY(7,"일",(date)->findWeekInterval(date,6));


    private final Integer weekCode;
    private final String name;
    private final Function<LocalDate,DateStartEnd> getWeekInterval;

    Week(Integer weekCode,String name,Function<LocalDate,DateStartEnd> getWeekInterval){
        this.weekCode = weekCode;
        this.name = name;
        this.getWeekInterval = getWeekInterval;
    }

    private static DateStartEnd findWeekInterval(LocalDate date,Integer before){
        return DateStartEnd.of(date.minusDays(before),DateTimeFrameConstants.WEEK_DAYS);
    }


    public static Week fromWeekCode(Integer weekCode){
        if(weekCode==null){
            throw new IllegalArgumentException("No weekCode");
        }

        for(Week week : Week.values()){
            if(week.weekCode.equals(weekCode)){
                return week;
            }
        }

        throw new IllegalArgumentException("No matching weekCode");
    }

    public DateStartEnd getWeekDuration(LocalDate date){
        return getWeekInterval.apply(date);
    }

    public String getName(){
        return name;
    }
}
