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
import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.category.entity.UserCategory;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.category.repository.CategoryRepository;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import com.myweapon.hourglass.title.entity.UserTitle;
import com.myweapon.hourglass.title.repository.UserTitleRepository;
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
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final TaskRepository taskRepository;
    private final UserTitleRepository userTitleRepository;

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
        List<UserCategory> userCategories = addDefaultCategories(user);
        addDefaultTasks(user,userCategories);
        addTitleRow(user);
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
    private void addTitleRow(User user){
        UserTitle userTitle = UserTitle.builder()
                .title1(false)
                .title2(false)
                .title3(false)
                .title4(false)
                .title5(false)
                .title6(false)
                .title7(false)
                .title8(false)
                .title9(false)
                .title10(false)
                .title11(false)
                .title12(false)
                .title13(false)
                .main_title(0)
                .user(user)
                .build();
        userTitleRepository.save(userTitle);
    }
}
