package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.respository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void defaultCategoryInit(){
        for(DefaultCategory defaultCategory : DefaultCategory.values()){
            Category category = categoryRepository.findByName(defaultCategory.getName())
                    .orElseGet(()-> {
                        Category categoryToPersist = Category.of(defaultCategory);
                        categoryRepository.save(categoryToPersist);
                        return categoryToPersist;
                    });

            defaultCategory.setId(category.getId());
        }
    }
}
