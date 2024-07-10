package com.myweapon.hourglass.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BurstTimeByCategories {
    private String categoryName;
    private Long totalBurst;
    private String color;
}
