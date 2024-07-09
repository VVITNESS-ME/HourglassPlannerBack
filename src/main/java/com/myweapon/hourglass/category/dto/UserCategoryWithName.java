package com.myweapon.hourglass.category.dto;

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
