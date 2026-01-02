package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.exception.ProductNotFoundException;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;

    Product createProduct(Long id, String title, String description, Double price, String category, String imageUrl);

    Product deleteProduct(Long id) throws ProductNotFoundException;

    Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl) throws ProductNotFoundException;

    List<Product> getAllProducts();
}
