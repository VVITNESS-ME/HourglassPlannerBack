package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Builder
    public Category(String name){
        this.name = name;
    }

    public static Category of(DefaultCategory defaultCategory){
        return Category.builder()
                .name(defaultCategory.getName())
                .build();
    }

}
