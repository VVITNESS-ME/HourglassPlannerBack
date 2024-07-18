package com.myweapon.hourglass.title.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class TitleDto {
    private int id;
    private String name;
    private String achieveCondition;
    private String titleColor;
}
