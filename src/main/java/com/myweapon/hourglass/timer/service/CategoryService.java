package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.category.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void defaultCategoryInit(){
        List<Category> categories = new ArrayList<>();
        for(DefaultCategory defaultCategory : DefaultCategory.values()){
            Category category = categoryRepository.findByName(defaultCategory.getName())
                    .orElseGet(()-> {
                        Category categoryToPersist = Category.of(defaultCategory);
                        categories.add(categoryToPersist);
                        return categoryToPersist;
                    });

            categoryRepository.saveAll(categories);
            defaultCategory.setId(category.getId());
        }
    }
}
