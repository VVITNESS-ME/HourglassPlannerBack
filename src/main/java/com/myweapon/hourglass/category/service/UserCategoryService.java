package com.myweapon.hourglass.category.service;

import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.security.entity.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;
    public ResponseEntity<ApiResponse<Category>> getUserCategory(User user){
        UserCAte
    }
}
