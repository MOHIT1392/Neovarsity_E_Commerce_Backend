package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;

import java.util.List;
import java.util.NoSuchElementException;

public interface CategoryService {
    Category createCategory(String categoryTitle);
    List<Product> getProductsInCategory(String category) throws NoSuchElementException;
    List<Category> getAllCategories() throws NullPointerException;
}
