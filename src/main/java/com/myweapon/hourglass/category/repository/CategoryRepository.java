package com.myweapon.hourglass.category.repository;

import com.myweapon.hourglass.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Optional<Category> findByName(String name);

    @Query("select c from Category c where c.id in :categoriesId")
    public List<Category> findCategoriesById(@Param("categoriesId") List<Long> categoriesId);
}
