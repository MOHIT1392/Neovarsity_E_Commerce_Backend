package com.scalerNeoVarsity.backendProject.repository;

import com.scalerNeoVarsity.backendProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Product save(Product product);

    Product findByDescription(String description);

    Product findByTitle(String title);

    Product findByTitleAndDescription(String title, String description);
}
