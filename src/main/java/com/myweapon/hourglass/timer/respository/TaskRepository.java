package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.dto.task.TaskWithUserCategory;
import com.myweapon.hourglass.timer.entity.Hourglass;
import com.myweapon.hourglass.timer.entity.Task;
import com.myweapon.hourglass.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("select new com.myweapon.hourglass.timer.dto.task.TaskWithUserCategory(t.id,t.title,c.name,uc.color) " +
            "from User u join UserCategory uc on u=uc.user join Task t on uc = t.userCategory join Category c on uc.category=c " +
            "where u.id =:userId " +
            "and t.isCompleted =:isCompleted")
    List<TaskWithUserCategory> findTaskWithUserCategoryByUserIdAndIsCompleted(@Param("userId")Long userId, @Param("isCompleted")Boolean isCompleted);

    @Transactional
    @Modifying
    @Query("update Task t set t.isCompleted = true where t.id = :tId and t.isDefault = false")
    Integer completeTask(Long tId);

    @Query("select t from Task t " +
            "join UserCategory uc on t.userCategory = uc " +
            "join User u on uc.user = u " +
            "where u.id=:userId and t.isDefault = true")
    List<Task> findDefaultTasksByUser(@Param("userId") Long userId);

    @Query("select t from Task t " +
            "join UserCategory uc on t.userCategory = uc " +
            "join Category c on uc.category = c " +
            "join User u on uc.user = u " +
            "where u.id=:userId and t.isDefault = true and c.name =:categoryName")
    Optional<Task> findDefaultTasksByUserAndCategoryName(@Param("userId") Long userId,@Param("categoryName")String categoryName);


    @Query("select t from Task t " +
            "join UserCategory uc on t.userCategory = uc " +
            "join User u on uc.user = u " +
            "where u.id=:userId")
    List<Task> findAllTasksByUser(@Param("userId") Long userId);

    @Query("select t from Task t " +
            "join Hourglass h on t = h.task " +
            "where h =:hourglass")

    Optional<Task> findByHourglass(@Param("hourglass") Hourglass hourglass);

}
