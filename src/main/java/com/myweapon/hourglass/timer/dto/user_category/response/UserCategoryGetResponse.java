package com.myweapon.hourglass.timer.dto.user_category.response;

import com.myweapon.hourglass.timer.dto.user_category.UserCategoryWithName;
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
