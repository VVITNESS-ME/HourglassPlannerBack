package com.myweapon.hourglass.security.entity;

import com.myweapon.hourglass.timer.entity.UserCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    private String email;
    private String password;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<UserCategory> userCategories = new ArrayList<>();

    @Builder
    private User(String email,String password,String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User of(String email,String password,String name){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
