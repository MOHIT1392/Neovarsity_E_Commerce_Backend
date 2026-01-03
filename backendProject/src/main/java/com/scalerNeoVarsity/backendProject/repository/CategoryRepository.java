package com.scalerNeoVarsity.backendProject.repository;


import com.scalerNeoVarsity.backendProject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);

    Category findByTitle(String title);
}

