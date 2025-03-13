package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.timer.dto.user_category.response.UserCategoryResponse;
import com.myweapon.hourglass.timer.dto.user_category.response.UserCategoryRequest;
import com.myweapon.hourglass.timer.dto.user_category.UserCategoryInfo;
import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.entity.UserCategory;
import com.myweapon.hourglass.timer.exception.TimerRestApiException;
import com.myweapon.hourglass.timer.respository.UserCategoryRepository;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.myweapon.hourglass.common.repository.utils.UpdateUtils.isNotUpdated;

@Service
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;
    private final CategoryService categoryService;

    @Transactional
    public ApiResponse<UserCategoryResponse> getUserCategory(User user){
        List<UserCategoryInfo> userCategoriesWithName = userCategoryRepository.getUserCategoriesWithName(user);

        UserCategoryResponse userCategoryResponse = UserCategoryResponse.of(userCategoriesWithName);

        return ApiResponse.success(userCategoryResponse);
    }

    //중복된 이름의 userCategory를 만들어낼 수 있음. 차후에 예외처리
    @Transactional
    public Long createUserCategory(UserCategoryRequest request, User user){
        Category category = categoryService
                .findOrCreateCategory(request.getCategoryName());

        UserCategory userCategory = UserCategory.of(user,category,request.getColor());
        return userCategoryRepository.save(userCategory).getId();
    }

    public ApiSuccess deleteUserCategory(Long userCategoryId){
        if(isNotUpdated(userCategoryRepository.deleteUserCategory(userCategoryId))){
            throw new RestApiException(ErrorType.USER_CATEGORY_NOT_EXISTS);
        }
        return ApiSuccess.body();
    }

    public UserCategory findUserCategoryById(Long userCategoryId){
        return userCategoryRepository.findById(userCategoryId).orElseThrow(()-> new TimerRestApiException(TimerRestApiException.NoSuchHourglass));
    }
}
