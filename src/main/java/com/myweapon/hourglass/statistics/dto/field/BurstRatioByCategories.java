package com.myweapon.hourglass.statistics.dto.field;

import lombok.Builder;
import java.util.List;

@Builder
public record BurstRatioByCategories(String categoryName, Float ratio) {
    public static List<BurstRatioByCategories> listFrom(List<BurstTimeByCategories> burstTimeByCategoriesList){
        Integer sum = burstTimeByCategoriesList.stream()
                .mapToInt(e->e.burstTime().intValue())
                .sum();
        return burstTimeByCategoriesList.stream().map((e)->from(e,sum)).toList();
    }

    public static BurstRatioByCategories from(BurstTimeByCategories burstTimeByCategories,Integer sum){
        return BurstRatioByCategories.builder()
                .categoryName(burstTimeByCategories.categoryName())
                .ratio(burstTimeByCategories.burstTime().floatValue()/sum)
                .build();
    }
}
