package com.dailycodework.dreamshop.service.category;

import com.dailycodework.dreamshop.Exception.CategoryAlreadyExistsException;
import com.dailycodework.dreamshop.Exception.CategoryNotfoundException;
import com.dailycodework.dreamshop.model.Category;
import com.dailycodework.dreamshop.repository.categoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final categoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new CategoryNotfoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }


    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName((c.getName())))
                .map(categoryRepository::save)
                .orElseThrow(()-> new CategoryAlreadyExistsException("Category not found"));
    }

    @Override
    public Category updateCategory(Category category, long id) {
        return Optional.ofNullable(getCategoryById(id))
            .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
        })
            .orElseThrow(()-> new CategoryNotfoundException("Category not found"));

    }
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,() ->
                {throw new CategoryNotfoundException("Category not found");
                });
    }
}
