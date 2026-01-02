package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.dto.FakeStoreProductDTO;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.service.ProductService;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

//This annotation here is going to tell SpringBoot that this is one of our important class so create an object of this
@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) {
        System.out.println("We are inside getSingleProduct method");
        return null;
    }

    @Override
    public Product createProduct(Long id, String title, String description,
                                 Double price, String category, String imageUrl) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(id);
        fakeStoreProductDTO.setTitle(title);
        fakeStoreProductDTO.setDescription(description);
        fakeStoreProductDTO.setPrice(price);
        fakeStoreProductDTO.setCategory(category);
        fakeStoreProductDTO.setImage(imageUrl);

        FakeStoreProductDTO response = restTemplate.postForObject("https://fakestoreapi.com/products",
                fakeStoreProductDTO, FakeStoreProductDTO.class);

        return response.getProduct();
    }

    @Override
    public Product deleteProduct(Long id) {
        Product deletedProduct = getSingleProduct(id);
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
        return deletedProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl) {
        System.out.println("Inside the update product in FakeStoreProductService API");
        Product existingProduct = getSingleProduct(id);

        ResponseEntity<Product> responseEntity = null;
        try {
            System.out.println("Updating the Product");
            if (title != null) {
                existingProduct.setTitle(title);
            }
            if (description != null) {
                existingProduct.setDescription(description);
            }
            if (price != null) {
                existingProduct.setPrice(price);
            }
            if (category != null) {
                existingProduct.setCategory(category);
            }
            if (imageUrl != null) {
                existingProduct.setImageUrl(imageUrl);
            }
            // Create HttpEntity with the updated product
            HttpEntity<Product> requestEntity = new HttpEntity<>(existingProduct);

            // Use exchange() method instead of patchForObject
            responseEntity = restTemplate.exchange(
                    "https://fakestoreapi.com/products/" + id,
                    HttpMethod.PUT,
                    requestEntity,
                    Product.class
            );
        } catch (RuntimeException re) {
            throw new RuntimeException("Product Not Found" + re);
        }
        return responseEntity.getBody();
    }

    @Override
    public List<Product> getAllProducts() {
        System.out.println("In the getAllProducts API in FKSPS");
        FakeStoreProductDTO[] listOfProducts =
                restTemplate.getForObject("https://fakestoreapi.com/products/",
                        FakeStoreProductDTO[].class);
        return new FakeStoreProductDTO().getListOfProducts(listOfProducts);
    }
}
