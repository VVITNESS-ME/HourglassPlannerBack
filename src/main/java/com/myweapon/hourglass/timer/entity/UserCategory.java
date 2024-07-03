package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name="user_category")
public class UserCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    String color;

    @Builder
    public UserCategory(User user,Category category,String color){
        this.user = user;
        this.category = category;
        this.color = color;
    }

}
