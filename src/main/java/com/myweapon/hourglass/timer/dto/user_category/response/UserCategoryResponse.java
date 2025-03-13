package com.myweapon.hourglass.timer.dto.user_category.response;

import com.myweapon.hourglass.timer.dto.user_category.UserCategoryInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class UserCategoryResponse {
    private List<UserCategoryInfo> userCategoriesWithName;

    public static UserCategoryResponse of(List<UserCategoryInfo> userCategoriesWithName){
        return UserCategoryResponse.builder()
                .userCategoriesWithName(userCategoriesWithName)
                .build();
    }
}
