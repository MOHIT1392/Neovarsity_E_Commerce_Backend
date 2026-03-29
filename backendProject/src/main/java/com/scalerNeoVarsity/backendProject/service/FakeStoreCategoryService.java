package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.dto.FakeStoreCategoryDTO;
import com.scalerNeoVarsity.backendProject.dto.FakeStoreProductDTO;
import com.scalerNeoVarsity.backendProject.exception.CategoryNotFoundException;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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

        FakeStoreProductDTO[] fakeStoreListOfProducts = null;
        try {
            //Fetch products for the given category from the API
            fakeStoreListOfProducts =
                    restTemplate.getForObject(
                            "https://fakestoreapi.com/products/category/" + category,
                            FakeStoreProductDTO[].class
                    );
            if (fakeStoreListOfProducts == null || fakeStoreListOfProducts.length == 0) {
                throw new CategoryNotFoundException("No products found for category " + category);
            }
        } catch (Exception e) {
            throw new NoSuchElementException("Error fetching products for category " + category, e);
        }

        return new FakeStoreProductDTO().getListOfProducts(fakeStoreListOfProducts);
    }

    public List<Category> getAllCategories() throws NullPointerException {
        String[] fakeStoreListOfCategories =
                restTemplate.getForObject("https://fakestoreapi.com/products/categories",
                        String[].class);

        if (fakeStoreListOfCategories == null) {
            throw new NullPointerException("No Categories found");
        }
        List<Category> listOfCategories = new ArrayList<>();

        for (String categoryTitle : fakeStoreListOfCategories) {
            Category category = new Category();
            category.setTitle(categoryTitle);
            listOfCategories.add(category);
        }

        return listOfCategories;
    }
}