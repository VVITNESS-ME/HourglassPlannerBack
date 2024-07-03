package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {

    @Query("select uc from UserCategory uc where uc.user = :user")
    public List<UserCategory> findAllByUser(@Param("user") User user);

    @Query("select uc from UserCategory uc where uc.user = :user and uc.category.name= :name ")
    public Optional<UserCategory> findByUserAndCategoryName(@Param("user") User user,@Param("name")String name);
}
