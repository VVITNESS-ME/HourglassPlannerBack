package com.myweapon.hourglass.security.entity;

import jakarta.persistence.*;
import lombok.*;

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
