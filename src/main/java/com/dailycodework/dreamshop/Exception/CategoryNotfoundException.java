package com.dailycodework.dreamshop.Exception;

public class CategoryNotfoundException extends RuntimeException{
    public CategoryNotfoundException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
