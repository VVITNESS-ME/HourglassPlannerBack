package com.myweapon.hourglass.schedule.repository;

import com.myweapon.hourglass.schedule.dto.Todo;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("select t from Task t " +
            "inner join UserCategory uc " +
            "on t.userCategory = uc " +
            "inner join Category c " +
            "on uc.category = c " +
            "where t.isDefault = true and c.name = :categoryName and uc.user = :user")
    public Optional<Task> findDefaultTaskByCategoryName(@Param("categoryName")String categoryName,@Param("user") User user);

    @Query("select new com.myweapon.hourglass.schedule.dto.Todo(t.id,t.title,c.name,uc.color) from Task t " +
            "inner join UserCategory uc " +
            "on t.userCategory = uc " +
            "inner join Category c " +
            "on uc.category =c " +
            "where t.isDefault = false and t.isCompleted = :isCompleted and uc.user = :user")
    public List<Todo> findTodoTasks(@Param("user")User user,Boolean isCompleted);

    @Transactional
    @Modifying
    @Query("update Task t set t.isCompleted = true where t.id = :tId and t.isDefault = false")
    public Integer completeTask(Long tId);
}
