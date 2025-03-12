package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.timer.entity.Category;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.respository.CategoryRepository;
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

    public Category findOrCreateCategory(String categoryName){
        Category category = categoryRepository
                .findByName(categoryName)
                .orElseGet(()->{
                    Category categoryNew = Category.of(categoryName);
                    categoryRepository.save(categoryNew);
                    return categoryNew;
                });
        return category;
    }

    public List<Category> findDefaultCategories(){
        List<Category> categories = new ArrayList<>();
        for(DefaultCategory category:DefaultCategory.values()){
            categories.add(findOrCreateCategory(category.getName()));
        }
        return categories;
    }
}
