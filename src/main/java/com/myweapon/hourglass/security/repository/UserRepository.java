package com.myweapon.hourglass.security.repository;

import com.myweapon.hourglass.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserByName(String name);
}

