package com.example.explore.service.impl;

import com.example.explore.model.entity.Category;
import com.example.explore.model.entity.enums.CategoryNameEnum;
import com.example.explore.repository.CategoryRepository;
import com.example.explore.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository
                .findByName(categoryNameEnum)
                .orElse(null);
    }
}
