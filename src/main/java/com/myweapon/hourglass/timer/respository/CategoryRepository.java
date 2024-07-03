package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Optional<Category> findByName(String name);
}
