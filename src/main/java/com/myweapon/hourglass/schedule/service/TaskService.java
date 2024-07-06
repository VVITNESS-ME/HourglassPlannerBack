package com.myweapon.hourglass.schedule.service;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.schedule.dto.Todo;
import com.myweapon.hourglass.schedule.dto.TodoGetResponse;
import com.myweapon.hourglass.schedule.dto.TodoPostRequest;
import com.myweapon.hourglass.schedule.dto.TodoPostResponse;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.entity.UserCategory;
import com.myweapon.hourglass.timer.respository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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

    public ResponseEntity<ApiResponse<TodoGetResponse>> getTodo(User user){
        List<Todo> schedules = taskRepository.findTodoTasks(user);
        TodoGetResponse response = TodoGetResponse.of(schedules);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
