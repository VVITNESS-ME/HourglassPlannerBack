package com.myweapon.hourglass.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {
    private Long taskId;
    private String title;
    private String userCategoryName;
    private String color;
}
