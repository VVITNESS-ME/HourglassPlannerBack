package com.myweapon.hourglass.timer.controller;

import com.myweapon.hourglass.timer.dto.user_category.response.UserCategoryGetResponse;
import com.myweapon.hourglass.timer.dto.user_category.response.UserCategoryRequest;
import com.myweapon.hourglass.timer.service.UserCategoryService;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.timer.service.UserCategoryTaskManger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-category")
public class UserCategoryController {
    private final UserCategoryService userCategoryService;
    private final UserCategoryTaskManger userCategoryTaskManger;

    @GetMapping("")
    public ApiResponse<UserCategoryGetResponse>getUserCategory(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userCategoryService.getUserCategory(userDetails.getUser());
    }

    @PostMapping("")
    public ApiSuccess addUserCategory(@RequestBody UserCategoryRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userCategoryTaskManger.createUserCategoryAndDefaultTask(userDetails.getUser(),request);
        return ApiSuccess.body();
    }

    @PutMapping("")
    public ApiSuccess deleteUserCategory(@RequestParam("userCategoryId")Long userCategoryId){
        return userCategoryService.deleteUserCategory(userCategoryId);
    }
}
