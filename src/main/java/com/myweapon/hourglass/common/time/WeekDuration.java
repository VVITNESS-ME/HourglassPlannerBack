package com.myweapon.hourglass.common.time;

import lombok.Builder;

import java.time.LocalDate;

public class WeekDuration extends DateStartEnd{
    public static WeekDuration of(DateStartEnd dateStartEnd){
        return new WeekDuration(dateStartEnd);
    }
    private WeekDuration(DateStartEnd dateStartEnd){
        super(dateStartEnd.getStart(),dateStartEnd.getEnd());
    }
    public LocalDate monday(){
        return super.getStart();
    }

    public LocalDate sunday(){
        return super.getEnd();
    }

}
