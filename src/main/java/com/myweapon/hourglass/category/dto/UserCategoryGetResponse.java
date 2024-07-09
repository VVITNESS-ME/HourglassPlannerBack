package com.myweapon.hourglass.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class UserCategoryGetResponse {
    private List<UserCategoryWithName> userCategoriesWithName;

    public static UserCategoryGetResponse of(List<UserCategoryWithName> userCategoriesWithName){
        return UserCategoryGetResponse.builder()
                .userCategoriesWithName(userCategoriesWithName)
                .build();
    }
}
