package com.myweapon.hourglass.timer.dto.task.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TaskPostRequest {
    private String title;
    private Long userCategoryId;
}
