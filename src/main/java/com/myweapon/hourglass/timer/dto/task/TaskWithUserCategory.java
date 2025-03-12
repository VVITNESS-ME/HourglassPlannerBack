package com.myweapon.hourglass.timer.dto.task;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class TaskWithUserCategory {
    private Long taskId;
    private String title;
    private String categoryName;
    private String color;
}
