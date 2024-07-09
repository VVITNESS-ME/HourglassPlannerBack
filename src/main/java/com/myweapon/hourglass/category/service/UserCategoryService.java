package com.myweapon.hourglass.category.service;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.category.dto.UserCategoryGetResponse;
import com.myweapon.hourglass.category.dto.UserCategoryPostRequest;
import com.myweapon.hourglass.category.dto.UserCategoryWithName;
import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.category.entity.UserCategory;
import com.myweapon.hourglass.category.repository.CategoryRepository;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.ApiSuccess;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.myweapon.hourglass.common.repository.utils.UpdateUtils.isNotUpdated;

@Service
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    public ResponseEntity<ApiResponse<UserCategoryGetResponse>> getUserCategory(User user){
        List<UserCategoryWithName> userCategoriesWithName = userCategoryRepository.getUserCategoriesWithName(user);

        UserCategoryGetResponse userCategoryGetResponse = UserCategoryGetResponse.of(userCategoriesWithName);

        return ResponseEntity.ok(ApiResponse.success(userCategoryGetResponse));
    }

    public ResponseEntity<ApiSuccess> addUserCategory(UserCategoryPostRequest request,User user){
        String categoryName = request.getCategoryName();
        Category category = categoryRepository
                .findByName(categoryName)
                .orElseGet(()->{
                    Category categoryNew = Category.of(categoryName);
                    categoryRepository.save(categoryNew);
                    return categoryNew;
                });

        UserCategory userCategory = UserCategory.of(user,category,request.getColor());
        userCategoryRepository.save(userCategory);

        Task task = Task.defaultOf(userCategory);
        taskRepository.save(task);

        return ResponseEntity.ok(ApiSuccess.body());
    }

    public ResponseEntity<ApiSuccess> deleteUserCategory(Long userCategoryId){
        if(isNotUpdated(userCategoryRepository.deleteUserCategory(userCategoryId))){
            throw new RestApiException(ErrorType.USER_CATEGORY_NOT_EXISTS);
        }

        return ResponseEntity.ok(ApiSuccess.body());
    }
}
