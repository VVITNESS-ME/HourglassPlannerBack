package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name="user_category")
public class UserCategory {
    private final static String SIZE_NOT_MATCH = "카테고리의 수와 색의 수가 같아야 합니다.";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    Category category;

    String color;

    private Boolean isDeleted = false;

    @Builder(access = AccessLevel.PRIVATE)
    private UserCategory(User user,Category category,String color){
        this.user = user;
        this.category = category;
        this.color = color;
    }

    public static UserCategory of(User user, Category category, String color){

        return UserCategory.builder()
                .user(user)
                .category(category)
                .color(color).build();
    }

    public static List<UserCategory> listOf(User user,List<Category> categories,List<String> colors){
        List<UserCategory> userCategories = new ArrayList<>();
        if(categories.size()!=colors.size()){
            throw new IllegalArgumentException(SIZE_NOT_MATCH);
        }
        for(int i=0;i<categories.size();i++){
            UserCategory userCategory = of(user,categories.get(i),colors.get(i));
            userCategories.add(userCategory);
        }
        return userCategories;
    }

}
