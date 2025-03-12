package com.myweapon.hourglass.security.repository;

import com.myweapon.hourglass.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findUserByEmail(String email);

    @Query("select u from User u where u.name = :name and u.isDeleted = false")
    public Optional<User> findUserByName(String name);

    @Query("update User u set u.name = :newName where u= :user")
    public Integer changeName(@Param("user") User user, @Param("newName")String nameName);

    @Query("update User u set u.password = :newPassword where u= :user")
    public Integer changePassword(@Param("user") User user, @Param("newPassword")String newPassword);
}

