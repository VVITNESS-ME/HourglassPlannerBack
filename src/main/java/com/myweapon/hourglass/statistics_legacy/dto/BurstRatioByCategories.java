package com.myweapon.hourglass.statistics_legacy.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class BurstRatioByCategories {
    private String categoryName;
    private Float ratio;
    private String color;

    public static List<BurstRatioByCategories> listFrom(List<BurstTimeByCategories> burstTimeByCategoriesList){
        Long sumOfBurst = burstTimeByCategoriesList.stream().mapToLong((BurstTimeByCategories::getTotalBurst)).sum();

        List<BurstRatioByCategories> byCategories = new ArrayList<>();
        if(sumOfBurst == 0){
            return byCategories;
        }

        return burstTimeByCategoriesList.stream().map((e)->{
            return of(e,sumOfBurst);
        }).toList();
    }

    public static BurstRatioByCategories of(BurstTimeByCategories burstTimeByCategories, Long denominator){
        return BurstRatioByCategories.builder()
                .categoryName(burstTimeByCategories.getCategoryName())
                .ratio(Float.valueOf(burstTimeByCategories.getTotalBurst())/denominator)
                .color(burstTimeByCategories.getColor())
                .build();
    }
}
