package com.scalerNeoVarsity.backendProject.service;


import com.scalerNeoVarsity.backendProject.exception.ProductNotFoundException;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.repository.CategoryRepository;
import com.scalerNeoVarsity.backendProject.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return product.get();
        }

        throw new ProductNotFoundException("Product not found in our database");
    }

//    @Override
//    public List<Product> getAllProducts() throws ProductNotFoundException {
//        Optional<List<Product>> listOfProducts = Optional.of(productRepository.findAll());
//        if (listOfProducts.isEmpty()) {
//            throw new ProductNotFoundException("No Products in the database");
//        }
//        return listOfProducts.get();
//    }


    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) throws ProductNotFoundException {
        Page<Product> listOfProducts = productRepository.findAll(
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(fieldName).ascending()
                )
        );

        if (!listOfProducts.hasContent()) {
            throw new ProductNotFoundException("Products not found in our database");
        }

        return listOfProducts;
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
            //This means category is not present in our database
            Category newCategory = new Category();
            newCategory.setTitle(categoryTitle);
            Category newRow = categoryRepository.save(newCategory);
            product.setCategory(newRow);
        } else {
            product.setCategory(currentCategory.get());
        }

        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        Product deletedProduct = getSingleProduct(id);
        try {
            deletedProduct.setDeleted(true);
            productRepository.delete(deletedProduct);
        } catch (Exception e) {
            throw new ProductNotFoundException("Product not found in our database");
        }
        return deletedProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl) throws ProductNotFoundException {
        Product existingProduct = getSingleProduct(id);

        if (title != null && !title.isEmpty()) {
            existingProduct.setTitle(title);
        }
        if (description != null && !description.isEmpty()) {
            existingProduct.setDescription(description);
        }
        if (price != null) {
            existingProduct.setPrice(price);
        }
        if (imageUrl != null && !imageUrl.isEmpty()) {
            existingProduct.setImageUrl(imageUrl);
        }

        if (category != null) {
            // Check if the category already exists in the database
            Optional<Category> existingCategory = categoryRepository.findByTitle(category.getTitle());
            if (existingCategory.isEmpty()) {
                // Save the new category
                Category newCategory = categoryRepository.save(category);
                existingProduct.setCategory(newCategory);
            } else {
                existingProduct.setCategory(existingCategory.get());
            }
        }

        return productRepository.save(existingProduct);
    }
}