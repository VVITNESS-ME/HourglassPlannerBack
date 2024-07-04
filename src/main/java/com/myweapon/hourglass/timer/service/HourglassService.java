package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.TimeUtils;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.dto.*;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.Task;
import com.myweapon.hourglass.timer.entity.UserCategory;
import com.myweapon.hourglass.timer.entity.UserHourglass;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.respository.HourglassRepository;
import com.myweapon.hourglass.timer.respository.TaskRepository;
import com.myweapon.hourglass.timer.respository.UserCategoryRepository;
import com.myweapon.hourglass.timer.respository.UserHourglassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HourglassService {

    private final HourglassRepository hourglassRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final TaskRepository taskRepository;
    private final UserHourglassRepository userHourglassRepository;


    public ResponseEntity<ApiResponse<HourglassResponse>> startHourglass(HourglassStartRequest request, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        if(isHourglassInProgress(user)){
            throw new RestApiException(ErrorType.HOURGLASS_IN_PROGRESS);
        }

        UserCategory userCategory = userCategoryRepository
                .findByUserAndCategoryName(user, DefaultCategory.OTHERS.getName())
                .orElseThrow(()->{
                    return new RestApiException(ErrorType.USER_CATEGORY_NOT_EXISTS);
                });

        Task task = Task.defaultOf(userCategory);

        Hourglass hourglass = Hourglass.toStartHourglass(request);
        hourglass.setTask(task);
        taskRepository.save(task);
        hourglassRepository.save(hourglass);

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHourGlass(hourglass)));
    }

    public ResponseEntity<ApiResponse<HourglassResponse>> endHourglass(HourglassEndRequest request ,User user){
        LocalDateTime end = TimeUtils.formatString(request.getTimeEnd());
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));

        UserCategory userCategory = userCategoryRepository.findByUserAndCategoryName(user,request.getCategoryName())
                .orElseThrow(()->new RestApiException(ErrorType.USER_CATEGORY_NOT_EXISTS));

        hourglass.end(userCategory,request);

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(request.getHId())));
    }

    public ResponseEntity<ApiResponse<HourglassResponse>> pauseHourglass(HourglassPauseRequest request){
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));

        if(!hourglass.pause()){
            throw new RestApiException(ErrorType.HOURGLASS_ALREADY_PAUSE);
        }

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(hourglass.getId())));
    }


    public ResponseEntity<ApiResponse<HourglassResponse>> resumeHourglass(HourglassResumeRequest request){
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));

        if(!hourglass.resume()){
            throw new RestApiException(ErrorType.HOURGLASS_NOT_PAUSE);
        }

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(hourglass.getId())));
    }

    private boolean isHourglassInProgress(User user){
        Optional<UserHourglass> hourglass = userHourglassRepository.findUserHourglassNotEndByUserId(user.getId());
        return hourglass.isPresent();
    }
}
