package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.Exception.ProductNotFoundException;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.response.ApiResponse;
import com.dailycodework.dreamshop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(new ApiResponse("Susccess" , products ));
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductByID(@PathVariable Long id){
        try {
            Product theProduct = productService.getProductId(id);
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @GetMapping("/product/{name}/product")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String name){
        try {
            List<Product> theProduct = productService.getProductsbyCategory(name);
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @GetMapping("/product/{brandName}/product")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brandName){
        try {
            List<Product> theProduct = productService.getProductsbyBrand(brandName);
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @GetMapping("/product/{name}/product")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestBody String category , String brand){
        try {
            List<Product> theProduct = productService.getProductsbyCategoryandbyBrand(category , brand);
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
}
