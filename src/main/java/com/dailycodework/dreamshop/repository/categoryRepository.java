package com.dailycodework.dreamshop.repository;

import com.dailycodework.dreamshop.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface categoryRepository extends CrudRepository<Category, Long> {
    Category findByName(String name);
}
