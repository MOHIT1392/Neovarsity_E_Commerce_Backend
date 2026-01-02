package com.scalerNeoVarsity.backendProject.exception;

public class ProductNotFoundException extends Exception {

    //Create an object of ProductNotFoundException class
    //And set the error message
    public ProductNotFoundException(String message) {
        super(message);
    }
}
