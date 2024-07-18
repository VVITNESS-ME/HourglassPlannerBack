package com.myweapon.hourglass.title.dto;

import com.myweapon.hourglass.title.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class UserTitleDto {
    private Boolean title1;
    private Boolean title2;
    private Boolean title3;
    private Boolean title4;
    private Boolean title5;
    private Boolean title6;
    private Boolean title7;
    private Boolean title8;
    private Boolean title9;
    private Boolean title10;
    private Boolean title11;
    private Boolean title12;
    private Boolean title13;
    private int main_title;

    public List<TitleDto> getTitleList(boolean pickCondition) {
        List<TitleDto> titleList = new ArrayList<>();
        for (Title title : Title.values()) {
            try {
                Field field = this.getClass().getDeclaredField("title" + title.getIndex());
                field.setAccessible(true);
                Boolean value = (Boolean) field.get(this);
                if (value != null && value == pickCondition) {
                    titleList.add(TitleDto.builder()
                            .id(title.getIndex())
                            .name(title.getTitleName())
                            .achieveCondition(title.getAchievementCondition())
                            .titleColor(title.getTitleColor())
                            .build());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return titleList;
    }

    public TitleResponse toTitleResponse(){
        return TitleResponse.builder()
                .achievedTitleList(getTitleList(true))
                .notAchievedTitleList(getTitleList(false))
                .build();
    }
}
