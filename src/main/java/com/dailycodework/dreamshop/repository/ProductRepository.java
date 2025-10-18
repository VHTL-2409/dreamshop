package com.dailycodework.dreamshop.repository;

import com.dailycodework.dreamshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Name(String categoryName);
    List<Product> findByBrand(String brand);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brandName, String productName);
    List<Product> findByCategory_NameAndBrand(String categoryName, String brandName);
    Long countByBrandAndName(String brandName, String name);
}
