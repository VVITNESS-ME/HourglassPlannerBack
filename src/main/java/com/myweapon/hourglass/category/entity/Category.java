package com.myweapon.hourglass.category.entity;

import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
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

    public static Category of(String categoryName){
        return Category.builder()
                .name(categoryName)
                .build();
    }
}
