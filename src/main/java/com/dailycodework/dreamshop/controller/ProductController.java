package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.Exception.ProductNotFoundException;
import com.dailycodework.dreamshop.Exception.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.request.AddproducRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;
import com.dailycodework.dreamshop.response.ApiResponse;
import com.dailycodework.dreamshop.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
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
            if(theProduct.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("No Product Found" , null));
            }
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName){
        try {
            List<Product> theProduct = productService.getProductsbyBrand(brandName);
            if(theProduct.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("No Product Found" , null));
            }
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category , @RequestParam  String brand){
        try {
            List<Product> theProduct = productService.getProductsbyCategoryandbyBrand(category , brand);
            if(theProduct.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("No Product Found" , null));
            }
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName , @RequestParam  String productName){
        try {
            List<Product> theProduct = productService.getProductsbyBrandandbyName(brandName , productName);
            if(theProduct.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("No Product Found" , null));
            }
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/product/{category}/all/product")
    public ResponseEntity<ApiResponse> getAllProductByCategory(@PathVariable String category){
        try {
            List<Product> theProduct = productService.getProductsbyCategory(category);
            if(theProduct.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("No Product Found" , null));
            }
            return ResponseEntity.ok(new ApiResponse("Found" , theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null
            ));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddproducRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Success !" ,  theProduct ));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product , @PathVariable Long id){
        try {
            Product theProduct = productService.updateProduct(product , id);
            return ResponseEntity.ok(new ApiResponse("Success !" ,  theProduct ));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deletePrduct(id);
            return ResponseEntity.ok(new ApiResponse("Success !" ,  null));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
