package com.example.explore.service;

import com.example.explore.model.entity.Category;
import com.example.explore.model.entity.enums.CategoryNameEnum;

public interface CategoryService {

    Category findCategoryByName(CategoryNameEnum categoryNameEnum);
}
