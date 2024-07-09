package com.myweapon.hourglass.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudySummeryWithCategory {
    Long userCategoryId;
    LocalDateTime start;
    LocalDateTime end;
    Integer burst;
    String color;
}
