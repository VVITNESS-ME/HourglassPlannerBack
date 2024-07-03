package com.myweapon.hourglass.timer.enumset;

import lombok.Getter;

import java.util.List;

@Getter
public enum DefaultCategory {
    READING("독서",1L,"#87ceeb"),
    ALGORITHM("알고리즘",2L,"#9acd32"),
    HOMEWORK("과제",3L,"#ffb6c1"),
    OTHERS("기타",4L,"#a9a9a9");

    private final String name;
    private Long id;
    private final String color;

    DefaultCategory(String name,Long id,String color){
        this.name = name;
        this.id = id;
        this.color = color;
    }

    public void setId(Long id){
        this.id = id;
    }
}
