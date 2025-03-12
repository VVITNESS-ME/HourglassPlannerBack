package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.user_category.response.UserCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserCategoryTaskManger {
    private final UserCategoryService userCategoryService;
    private final TaskService taskService;
    public void createUserCategoryAndDefaultTask(User user, UserCategoryRequest request){
        Long userCategoryId = userCategoryService.createUserCategory(request,user);
        taskService.createDefaultTask(userCategoryId);
    }
}
