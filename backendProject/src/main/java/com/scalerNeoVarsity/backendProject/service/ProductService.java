package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;

public interface ProductService {
    Product getSingleProduct(Long id);
    Product createProduct(Long id, String title, String description, Double price, String category, String imageUrl);
    Product deleteProduct(Long id);
    Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl);
}
