package com.myweapon.hourglass.timer.dto;

import com.myweapon.hourglass.category.entity.Category;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class StudySummeryWithCategoryName {
    private String categoryName;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer burst;
    private String color;

    public static StudySummeryWithCategoryName of(StudySummeryWithCategory studySummeryWithCategory, Category category){
        return StudySummeryWithCategoryName.builder()
                .categoryName(category.getName())
                .start(studySummeryWithCategory.getStart())
                .end(studySummeryWithCategory.getEnd())
                .burst(studySummeryWithCategory.getBurst())
                .color(studySummeryWithCategory.getColor())
                .build();
    }
}
