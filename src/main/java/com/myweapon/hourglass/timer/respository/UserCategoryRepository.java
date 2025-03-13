package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.dto.user_category.UserCategoryInfo;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {

    @Query("select uc from UserCategory uc where uc.user = :user")
    public List<UserCategory> findAllByUser(@Param("user") User user);

    @Query("select uc from UserCategory uc where uc.user.id = :user_id and uc.category.name= :name ")
    public Optional<UserCategory> findByUserAndCategoryName(@Param("user_id") Long user_id,@Param("name")String name);


    @Query("select new com.myweapon.hourglass.timer.dto.user_category.UserCategoryInfo(uc.id,c.name,uc.color) " +
            "from UserCategory uc " +
            "inner join Category c " +
            "on uc.category = c " +
            "where uc.user = :user and uc.isDeleted = false ")
    public List<UserCategoryInfo> getUserCategoriesWithName(User user);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update UserCategory uc set uc.isDeleted = true where uc.id=:userCategoryId")
    public Integer deleteUserCategory(@Param("userCategoryId") Long userCategoryId);
}
