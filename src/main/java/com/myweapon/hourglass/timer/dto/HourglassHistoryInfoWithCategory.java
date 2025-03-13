package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.timer.dto.user_category.UserCategoryInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HourglassHistoryInfoWithCategory {
    private DefaultHourglassHistoryInfo defaultHourglassHistoryInfo;
    private UserCategoryInfo userCategoryInfo;
    public static HourglassHistoryInfoWithCategory of(HourglassHistoryWithCategory hourglassHistoryWithCategory){
        return HourglassHistoryInfoWithCategory
                .builder()
                .defaultHourglassHistoryInfo(DefaultHourglassHistoryInfo.of(hourglassHistoryWithCategory.getHourglassHistory()))
                .userCategoryInfo(UserCategoryInfo.of(
                        hourglassHistoryWithCategory.getUserCategoryId()
                        , hourglassHistoryWithCategory.getCategoryName()
                        , hourglassHistoryWithCategory.getColor()
                        )
                ).build();
    }
}
