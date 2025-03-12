package com.myweapon.hourglass.timer.dto.task.response;


import com.myweapon.hourglass.timer.dto.task.TaskWithUserCategory;
import com.myweapon.hourglass.timer.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class TaskGetResponse {
    private List<TaskWithUserCategory> taskWithUserCategories;
    public static TaskGetResponse of(List<TaskWithUserCategory> taskWithUserCategories){
        return TaskGetResponse.builder().taskWithUserCategories(taskWithUserCategories).build();
    }
}
