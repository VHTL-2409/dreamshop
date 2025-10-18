package com.dailycodework.dreamshop.service.product;

import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.request.AddproducRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddproducRequest request);

    List<Product> getProducts();
    Product getProductId(Long id);
    void deletePrduct(Long id);
    Product updateProduct(ProductUpdateRequest product , Long productId);
    List<Product> getAllProduct();
    List<Product> getProductsbyCategory(String categoryName);
    List<Product> getProductsbyBrand(String brand);
    List<Product> getProductsbyCategoryandbyBrand(String category, String brand);
    List<Product> getProductbyName(String name);
    List<Product> getProductsbyBrandandbyName(String name, String brand);
    Long countProductByBrandAndName(String brand , String name);
}
