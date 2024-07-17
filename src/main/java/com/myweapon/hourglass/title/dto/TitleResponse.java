package com.myweapon.hourglass.title.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class TitleResponse {
    private List<TitleDto> achievedTitleList;
    private List<TitleDto> notAchievedTitleList;
}
