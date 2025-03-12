package com.myweapon.hourglass.timer.dto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TaskPostResponse {
    private Long taskId;
    public static TaskPostResponse of(Long taskId){
        return TaskPostResponse.builder().taskId(taskId).build();
    }
}
