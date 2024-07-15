package com.myweapon.hourglass.schedule.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.schedule.dto.*;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.category.entity.UserCategory;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserCategoryRepository userCategoryRepository;

    public ResponseEntity<ApiResponse<TodoPostResponse>> addTodo(TodoPostRequest request, User user){
        UserCategory userCategory = userCategoryRepository.findById(request.getUserCategoryId())
                .orElseThrow(()->new RestApiException(ErrorType.USER_CATEGORY_NOT_EXISTS));
        Task task = Task.todoOf(request,userCategory);
        taskRepository.save(task);
        TodoPostResponse response = TodoPostResponse.of(task.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    public ResponseEntity<ApiResponse<TodoGetResponse>> getTodo(User user,Boolean isCompleted){
        List<Todo> schedules = taskRepository.findTodoTasks(user,isCompleted);
        TodoGetResponse response = TodoGetResponse.of(schedules);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    public ResponseEntity<ApiResponse<ApiSuccess>> completeTodo(Long taskId){
        if(taskRepository.completeTask(taskId)==0){
            throw new RestApiException(ErrorType.TASK_NOT_EXISTS);
        }
        return ResponseEntity.ok(ApiResponse.success(ApiSuccess.body()));
    }
}
