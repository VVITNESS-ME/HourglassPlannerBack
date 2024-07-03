package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.TimeUtils;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassResponse;
import com.myweapon.hourglass.timer.dto.HourglassStartRequest;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.Task;
import com.myweapon.hourglass.timer.entity.UserCategory;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.respository.HourglassRepository;
import com.myweapon.hourglass.timer.respository.TaskRepository;
import com.myweapon.hourglass.timer.respository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HourglassService {
    private final HourglassRepository hourglassRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final TaskRepository taskRepository;


    @Transactional
    public ResponseEntity<ApiResponse<HourglassResponse>> startHourglass(HourglassStartRequest request, UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        UserCategory userCategory = userCategoryRepository
                .findByUserAndCategoryName(user, DefaultCategory.OTHERS.getName())
                .orElseThrow();
        Task task = Task.defaultOf(userCategory);
        taskRepository.save(task);

        Hourglass hourglass = Hourglass.toStartHourglass(request);
        hourglass.setTask(task);
        hourglassRepository.save(hourglass);

//        Hourglass hourglass = Hourglass.toStartHourglass(request);
//        hourglassRepository.save(hourglass);
//
//        UserCategory userCategory = userCategoryRepository
//                .findByUserAndCategoryName(user, DefaultCategory.OTHERS.getName())
//                .orElseThrow();
//        Task task = Task.defaultOf(userCategory);
//        taskRepository.save(task);
//
//        hourglass.setTask(task);
//        System.out.println(hourglass.getTask());

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHourGlass(hourglass)));
    }

    public ResponseEntity<ApiResponse<HourglassResponse>> endHourglass(HourglassEndRequest request, UserDetailsImpl userDetails){
        LocalDateTime end = TimeUtils.formatString(request.getTimeEnd());
        try{
            hourglassRepository.updateEndAndBurstById(request.getHId(),end,request.getTimeBurst());
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(request.getHId())));
    }
}
