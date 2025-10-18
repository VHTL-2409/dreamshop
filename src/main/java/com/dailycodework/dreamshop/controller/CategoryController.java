package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.model.Category;
import com.dailycodework.dreamshop.response.ApiResponse;
import com.dailycodework.dreamshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{api.prefix}/categories")
public class CategoryController {
    public final CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found! " , categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Cant Found !", INTERNAL_SERVER_ERROR));

        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategories(@RequestBody Category name){
        try {
            Category newCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("success !" ,newCategory));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("erorr" , INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category theCategory = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found" ,  theCategory));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND ).body(new ApiResponse(e.getMessage() , null
            ));
        }
    }
    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found" ,  theCategory));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND ).body(new ApiResponse(e.getMessage() , null
            ));
        }
    }
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Success!" , null));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND ).body(new ApiResponse(e.getMessage() , null
            ));
        }
    }
    @PutMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id , @RequestBody Category categoryUpdate) {
        try {
            Category theCategory = categoryService.updateCategory(categoryUpdate , id);
            return ResponseEntity.ok(new ApiResponse("Found" ,  theCategory));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND ).body(new ApiResponse(e.getMessage() , null
            ));
        }
    }
}
