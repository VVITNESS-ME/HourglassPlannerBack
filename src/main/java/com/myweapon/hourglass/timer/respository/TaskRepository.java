package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Member;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("select t from Task t " +
            "inner join UserCategory uc " +
            "on t.userCategory = uc " +
            "inner join Category c " +
            "on uc.category = c " +
            "where t.isDefault = true and c.name = :categoryName")
    public Optional<Task> findDefaultTaskByCategoryName(@Param("categoryName")String categoryName);
}
