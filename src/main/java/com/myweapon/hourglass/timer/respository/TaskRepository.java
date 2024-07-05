package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Member;

public interface TaskRepository extends JpaRepository<Task,Long> {
//    @Query("select t from Task t where t.isDefault = true and t.userCategory")
//    public Task findDefaultTaskByCategoryName();
}
