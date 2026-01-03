package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.exception.ProductNotFoundException;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.repository.CategoryRepository;
import com.scalerNeoVarsity.backendProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("originalProductService")
public class OriginalProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public OriginalProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return product.get();
        }

        throw new ProductNotFoundException("Product not found in database");
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Long id, String title, String description, Double price, String categoryTitle, String imageUrl) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Optional<Category> currentCategory = categoryRepository.findByTitle(categoryTitle);
        if (currentCategory.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setTitle(categoryTitle);
            Category newRow = categoryRepository.save(newCategory);
            product.setCategory(newRow);
        } else {
            product.setCategory(currentCategory.get());
        }

        //Saving the product in the repository as well as returning it at the same time.
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl) throws ProductNotFoundException {
        return null;
    }
}