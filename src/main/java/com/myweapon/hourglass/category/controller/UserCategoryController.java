package com.myweapon.hourglass.category.controller;

import com.myweapon.hourglass.category.dto.UserCategoryGetResponse;
import com.myweapon.hourglass.category.dto.UserCategoryPostRequest;
import com.myweapon.hourglass.category.service.UserCategoryService;
import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.ApiSuccess;
import com.myweapon.hourglass.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userCategory")
public class UserCategoryController {
    private final UserCategoryService userCategoryService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<UserCategoryGetResponse>> getUserCategory(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userCategoryService.getUserCategory(userDetails.getUser());
    }

    @PostMapping("/")
    public ResponseEntity<ApiSuccess> addUserCategory(@RequestBody UserCategoryPostRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userCategoryService.addUserCategory(request,userDetails.getUser());
    }

    @PutMapping("/")
    public ResponseEntity<ApiSuccess> deleteUserCategory(@RequestParam("userCategoryId")Long userCategoryId){
        return userCategoryService.deleteUserCategory(userCategoryId);
    }
}
