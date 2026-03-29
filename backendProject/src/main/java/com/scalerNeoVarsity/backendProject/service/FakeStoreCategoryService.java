package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.dto.FakeStoreCategoryDTO;
import com.scalerNeoVarsity.backendProject.exception.CategoryNotFoundException;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Service("fakeStoreCategoryService")
public class FakeStoreCategoryService implements CategoryService {

    private RestTemplate restTemplate;

    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Category createCategory(String categoryTitle) {
        //In FakeStore there is no API hit for createCategory
//        FakeStoreCategoryDTO fakeStoreCategoryDTO = new FakeStoreCategoryDTO();
//        fakeStoreCategoryDTO.setId(id);
//        fakeStoreCategoryDTO.setCategoryTitle(categoryTitle);
//
//        fakeStoreCategoryDTO
        return null;
    }

    public List<Product> getProductsInCategory(String category) throws NoSuchElementException {
        return List.of();
    }

    public List<Category> getAllCategories() throws NullPointerException {
        FakeStoreCategoryDTO[] fakeStoreListOfCategories =
                restTemplate.getForObject("https://fakestoreapi.com/products/categories/",
                        FakeStoreCategoryDTO[].class);

        if (fakeStoreListOfCategories == null) {
            throw new NullPointerException("No Categories found");
        }
        return new FakeStoreCategoryDTO().getListOfCategories(fakeStoreListOfCategories);
    }
}