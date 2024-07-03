package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
