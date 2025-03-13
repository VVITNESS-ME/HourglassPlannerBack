package com.myweapon.hourglass.timer.dto.user_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserCategoryInfo {
    private Long userCategoryId;
    private String categoryName;
    private String color;

    public static UserCategoryInfo of(Long userCategoryId,String categoryName,String color){
        return UserCategoryInfo
                .builder()
                .userCategoryId(userCategoryId)
                .categoryName(categoryName)
                .color(color).build();
    }
}
