package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.security.repository.UserRepository;
import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import com.myweapon.hourglass.security.jwt.JwtService;
import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.category.entity.UserCategory;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.category.repository.CategoryRepository;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final TaskRepository taskRepository;

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

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RestApiException(ErrorType.NO_EMAIL_OR_PASSWORD);
        }
        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse data = JwtAuthenticationResponse.of(jwt);
        return ResponseEntity.ok(ApiResponse.<JwtAuthenticationResponse>success(data));
    }

    private void initUser(User user){
        List<UserCategory> userCategories = addDefaultCategories(user);
        addDefaultTasks(user,userCategories);
    }

    private List<UserCategory> addDefaultCategories(User user){
        List<Category> defaultCategories = categoryRepository.findCategoriesById(DefaultCategory.getAllId());
        List<String> colors = DefaultCategory.getAllColor();
        List<UserCategory> userCategories = UserCategory.listOf(user,defaultCategories,colors);

        userCategoryRepository.saveAll(userCategories);
        return userCategories;
    }

    private void addDefaultTasks(User user,List<UserCategory> userCategories){
        for(UserCategory userCategory : userCategories){
            taskRepository.save(Task.defaultOf(userCategory));
        }
    }
}
