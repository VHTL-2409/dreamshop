package com.dailycodework.dreamshop.Exception;

import org.aspectj.bridge.IMessage;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {

        super(message);
        super(categoryNotFound);
    }
}
