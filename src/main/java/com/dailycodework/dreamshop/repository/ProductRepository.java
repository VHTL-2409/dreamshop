package com.dailycodework.dreamshop.repository;

import com.dailycodework.dreamshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category) ;
    List<Product> findByBrand(String brand);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brand, String name);
    List<Product> findByCategoryAndBrand(String category, String brand);
    Long countByBranAndName(String category, String name);

}
