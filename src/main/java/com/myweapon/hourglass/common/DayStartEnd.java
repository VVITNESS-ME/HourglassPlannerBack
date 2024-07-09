package com.myweapon.hourglass.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@AllArgsConstructor
@Getter
public class DayStartEnd {
    private LocalDate start;
    private LocalDate end;
}
