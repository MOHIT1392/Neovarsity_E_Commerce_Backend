package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.service.ProductService;

import org.springframework.stereotype.Service;

import java.util.List;

//This annotation here is going to tell SpringBoot that this is one of our important class so create an object of this
@Service
public class FakeStoreProductService implements ProductService {

    //Inside this, fake store is going to be third party service

    @Override
    public Product getSingleProduct(Long id) {
        System.out.println("We are inside getSingleProduct method");
        return null;
    }

    @Override
    public Product createProduct(Long id, String title, String description, Double price, String category, String imageUrl) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl) {
        return null;
    }
}
