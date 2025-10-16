package com.dailycodework.dreamshop.service.category;

import com.dailycodework.dreamshop.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,long id);
    void deleteCategory(Long id);
}
