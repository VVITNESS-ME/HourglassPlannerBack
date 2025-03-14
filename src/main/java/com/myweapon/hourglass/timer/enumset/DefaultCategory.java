package com.myweapon.hourglass.timer.enumset;

import com.myweapon.hourglass.timer.exception.DefaultCategoryException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum DefaultCategory {
    READING("독서",1L,"#EAD1BC"),
    ALGORITHM("알고리즘",2L,"#EBEBBD"),
    HOMEWORK("과제",3L,"#CAEBEA"),
    OTHERS("기타",4L,"#E8C6E0");

    private final String name;
    private Long id;
    private final String color;

    DefaultCategory(String name,Long id,String color){
        this.name = name;
        this.id = id;
        this.color = color;
    }
    public static List<Long> getAllId(){
        return Arrays.stream(DefaultCategory.values())
                .map(DefaultCategory::getId)
                .toList();
    }
    public static List<String> getAllColor(){
        return Arrays.stream(DefaultCategory.values())
                .map(DefaultCategory::getColor)
                .toList();
    }

    public static String getColorOf(String name){
        return Arrays.stream(DefaultCategory.values())
                .filter((e)->e.getName().equals(name))
                .findAny()
                .orElseThrow(()->new DefaultCategoryException(DefaultCategoryException.NoSuchDefaultCategory))
                .getColor();
    }

    public void setId(Long id){
        this.id = id;
    }
}
