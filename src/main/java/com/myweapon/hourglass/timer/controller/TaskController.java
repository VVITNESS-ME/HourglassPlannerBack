package com.myweapon.hourglass.timer.controller;


import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.timer.dto.task.request.TaskPostRequest;
import com.myweapon.hourglass.timer.dto.task.response.TaskGetResponse;
import com.myweapon.hourglass.timer.dto.task.request.TaskPatchRequest;
import com.myweapon.hourglass.timer.dto.task.response.TaskPostResponse;
import com.myweapon.hourglass.timer.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Slf4j
public class TaskController {
    private final TaskService taskService;

    //페이지네이션 구현하기
    @GetMapping("")
    public ApiResponse<TaskGetResponse> getTasks(@AuthenticationPrincipal UserDetailsImpl userDetails
            , @RequestParam("isCompleted")Boolean isCompleted){
        return taskService.findTasksByUserIdAndIsCompleted(userDetails.getUser().getId(), isCompleted);
    }

    @PostMapping("")
    public ApiResponse<TaskPostResponse> createTask(@AuthenticationPrincipal UserDetailsImpl userDetails
            ,@RequestBody TaskPostRequest request){
        return ApiResponse.success(taskService.createCustomTask(request,userDetails.getUser()));
    }

    @PatchMapping("/{taskId}")
    public ApiSuccess updateTask(@AuthenticationPrincipal UserDetailsImpl userDetails
            ,@PathVariable("taskId")Long taskId){
        taskService.completeTask(taskId);
        return ApiSuccess.body();
    }
}
