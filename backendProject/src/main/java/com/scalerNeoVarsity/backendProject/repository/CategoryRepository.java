package com.scalerNeoVarsity.backendProject.repository;


import com.scalerNeoVarsity.backendProject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);

    Optional<Category> findByTitle(String title);
}

