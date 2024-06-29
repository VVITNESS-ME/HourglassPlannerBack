package com.myweapon.hourglass.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder
    public User(String name, String email, String password, String roles) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
