package com.myweapon.hourglass.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TodoPostRequest {
    private String title;
    private Long userCategoryId;
}
