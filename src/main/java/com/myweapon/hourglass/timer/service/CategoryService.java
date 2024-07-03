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
            Optional<Category> optionalCategory = categoryRepository.findByName(defaultCategory.getName());
            Category category;
            if(optionalCategory.isPresent()){
                category = optionalCategory.get();
            }
            else{
                category = Category.of(defaultCategory);
                categoryRepository.save(category);
            }
            defaultCategory.setId(category.getId());
        }
    }
}
