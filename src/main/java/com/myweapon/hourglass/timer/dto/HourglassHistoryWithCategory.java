package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.entity.HourglassHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HourglassHistoryWithCategory {
    private HourglassHistory hourglassHistory;
    private Long userCategoryId;
    private String categoryName;
    private String color;

}
