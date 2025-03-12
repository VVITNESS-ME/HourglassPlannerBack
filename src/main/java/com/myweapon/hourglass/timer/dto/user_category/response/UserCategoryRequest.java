package com.myweapon.hourglass.timer.dto.user_category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryRequest {
    private String categoryName;
    private String color;
    public static UserCategoryRequest of(String categoryName,String color){
        return UserCategoryRequest
                .builder()
                .categoryName(categoryName)
                .color(color)
                .build();
    }
}
