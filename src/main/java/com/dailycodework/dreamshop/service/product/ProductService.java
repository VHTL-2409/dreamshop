package com.dailycodework.dreamshop.service.product;

import com.dailycodework.dreamshop.Exception.ProductNotFoundException;
import com.dailycodework.dreamshop.model.Category;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.repository.categoryRepository;
import com.dailycodework.dreamshop.request.AddproducRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;
import com.dailycodework.dreamshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final categoryRepository categoryRepository;

    @Override
    public Product addProduct(AddproducRequest request) {
        //check if the category is found -> set it as new product category
        //if no save it as new category
        //The set as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(CreateProduct(request,category));

    }

    private Product CreateProduct(AddproducRequest request, Category category) {
      return new Product(
              request.getName() ,
              request.getBrand() ,
              request.getPrice() ,
              request.getInventory(),
              request.getDescription(),
              category
      );
    }
    @Override
    public List<Product> getProducts() {
        return  productRepository.findAll();
    }

    @Override
    public Product getProductId(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found"));

    }

    @Override
    public void deletePrduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()->{throw  new ProductNotFoundException("Product not found");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct->UpdateExistingProdct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
    }

    private Product UpdateExistingProdct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsbyCategory(String categoryName) {
        return productRepository.findByCategory_Name(categoryName);
    }

    @Override
    public List<Product> getProductsbyBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsbyCategoryandbyBrand(String categoryName, String brandName) {
        return productRepository.findByCategory_NameAndBrand(categoryName, brandName);
    }

    @Override
    public List<Product> getProductbyName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsbyBrandandbyName( String brandName,String categoryName) {
        return productRepository.findByBrandAndName(brandName, categoryName);
    }

    @Override
    public Long countProductByBrandAndName(String brandName, String name) {
        return productRepository.countByBrandAndName(brandName, name);
    }
}
