package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.timer.dto.task.TaskWithUserCategory;
import com.myweapon.hourglass.timer.dto.task.request.TaskPostRequest;
import com.myweapon.hourglass.timer.dto.task.response.TaskGetResponse;
import com.myweapon.hourglass.timer.dto.task.response.TaskPostResponse;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.Task;
import com.myweapon.hourglass.timer.exception.TimerRestApiException;
import com.myweapon.hourglass.timer.respository.TaskRepository;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.entity.UserCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
//한번 리팩토링 해야될듯 task 자체를 가져오는 메서드, 응답에 필요한 정보로 바꾸는 메서드, 상태 바꾸는 메서드(디폴트와 커스텀에 따라 다름)
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserCategoryService userCategoryService;

    public TaskPostResponse createCustomTask(TaskPostRequest request, User user){
        UserCategory userCategory = userCategoryService.findUserCategoryById(user.getId());
        Task task = Task.customOf(request.getTitle(),userCategory);
        taskRepository.save(task);
        return TaskPostResponse.of(task.getId());
    }

    public ApiResponse<TaskGetResponse> findTasksByUserIdAndIsCompleted(Long userId, Boolean isCompleted){
        List<TaskWithUserCategory> tasksWithUserCategory= taskRepository.findTaskWithUserCategoryByUserIdAndIsCompleted(userId,isCompleted);
        return ApiResponse.success(TaskGetResponse.of(tasksWithUserCategory));
    }

    //default 하나인지 검사하는 로직 추가하기
    @Transactional
    public Long createDefaultTask(Long userCategoryId){
        UserCategory userCategory = userCategoryService.findUserCategoryById(userCategoryId);
        Task taskDefault = Task.defaultOf(userCategory);
        return taskRepository.save(taskDefault).getId();
    }

    @Transactional
    public void changeUserCategory(Long taskId,Long userCategoryId){
        Task task = findTaskByTaskId(taskId);
        UserCategory userCategory = userCategoryService.findUserCategoryById(userCategoryId);
        task.setUserCategory(userCategory);
    }


    //모래시계가 돌아가는 task라면 종료를 못하게 해야됨.
    public ApiSuccess completeTask(Long taskId){
        if(taskRepository.completeTask(taskId)==0){
            throw new RestApiException(ErrorType.TASK_NOT_EXISTS);
        }
        return ApiSuccess.body();
    }

    public List<Task> findDefaultTasksOfUser(User user){
        return taskRepository.findDefaultTasksByUser(user.getId());
    }

    //기타 특정 이름을 가진 디폴트 태스크 가져오기
    public Task findDefaultTasksByUserIdAndCategoryName(Long userId,String categoryName){
        return taskRepository
                .findDefaultTasksByUserAndCategoryName(userId,categoryName)
                .orElseThrow(()->new TimerRestApiException(TimerRestApiException.NoSuchCategoryWithName));
    }

    public Task findTaskByHourglass(Hourglass hourglass){
        return taskRepository
                .findByHourglass(hourglass)
                .orElseThrow(()->new TimerRestApiException(TimerRestApiException.NoSuchTask));
    }

    /**
     * taskId에 해당하는 task반환 없다면 TimerNoSuchElementException 반환
     * @param taskId
     * @return
     */
    public Task findTaskByTaskId(Long taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(()->new TimerRestApiException(TimerRestApiException.NoSuchTask));

    }
}
