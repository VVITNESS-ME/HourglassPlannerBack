package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.security.repository.UserRepository;
import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import com.myweapon.hourglass.security.jwt.JwtService;
import com.myweapon.hourglass.timer.dto.user_category.response.UserCategoryRequest;
import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.entity.Task;
import com.myweapon.hourglass.timer.entity.UserCategory;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.service.CategoryService;
import com.myweapon.hourglass.timer.service.UserCategoryTaskManger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CategoryService categoryService;
    private final UserCategoryTaskManger userCategoryTaskManger;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signup(SignUpRequest request) {
        Optional<User> foundByEmail = userRepository.findUserByEmail(request.getEmail());
        if(foundByEmail.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_EMAIL);
        }

        Optional<User> foundByName = userRepository.findUserByName(request.getName());
        if(foundByName.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_NAME);
        }

        User user = User.of(request.getEmail(),passwordEncoder.encode(request.getPassword()), request.getName());

        userRepository.save(user);

        initUser(user);

        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse data = JwtAuthenticationResponse.of(jwt);
        return ResponseEntity.ok(ApiResponse.<JwtAuthenticationResponse>success(data));
    }

    @Override
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signin(SignInRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new RestApiException(ErrorType.NO_EMAIL_OR_PASSWORD));

        if(user.getIsDeleted()){
            throw new RestApiException(ErrorType.DELETED_USER);
        }

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RestApiException(ErrorType.NO_EMAIL_OR_PASSWORD);
        }

        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse data = JwtAuthenticationResponse.of(jwt);
        return ResponseEntity.ok(ApiResponse.<JwtAuthenticationResponse>success(data));
    }

    private void initUser(User user){
        addDefaultUserCategories(user);
    }

    private void addDefaultUserCategories(User user){
        List<Category> defaultCategories = categoryService.findDefaultCategories();
        for(Category defaultCategory:defaultCategories){
            String categoryName = defaultCategory.getName();
            userCategoryTaskManger
                    .createUserCategoryAndDefaultTask(user, UserCategoryRequest.of(categoryName,DefaultCategory.getColorOf(categoryName)));
        }
    }
}
