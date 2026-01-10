package com.scalerNeoVarsity.backendProject.repository;

import com.scalerNeoVarsity.backendProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scalerNeoVarsity.backendProject.repository.projections.ProductProjection;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Product save(Product product);

    Product findByDescription(String description);

    Product findByTitle(String title);

    Product findByTitleAndDescription(String title, String description);

    @Query("SELECT p FROM Product AS p WHERE p.category.id =:categoryId")
    List<Product> getProductByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT * FROM Product AS p WHERE p.category_id =:categoryId", nativeQuery = true)
    List<Product> getProductByCategoryIdByNativeQuery(@Param("categoryId") Long categoryId);

    @Query("SELECT p.title AS title, p.id AS id FROM Product AS p WHERE p.category.id =:categoryId")
    List<ProductProjection> getProductByCategoryIdUsingProjections(@Param("categoryId") Long categoryId);
}
