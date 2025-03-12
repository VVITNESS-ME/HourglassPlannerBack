package com.myweapon.hourglass.timer.dto.user_category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCategoryWithName {
    private Long userCategoryId;
    private String categoryName;
    private String color;
}
