package com.scalerNeoVarsity.backendProject.service;

import com.scalerNeoVarsity.backendProject.dto.FakeStoreProductDTO;
import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.scalerNeoVarsity.backendProject.exception.ProductNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    @Autowired
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        System.out.println("We are inside the single product in FakeStoreProductService");
        Product redisProduct = (Product) redisTemplate
                .opsForHash()
                .get("PRODUCTS", "PRODUCTS_" + id);

        if (redisProduct != null) {
            //Cache Hit
            return redisProduct;
        }
        FakeStoreProductDTO fakeStoreProductDTO =
                restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                        FakeStoreProductDTO.class);

//        System.out.println(fakeStoreProductDTO.toString());
        if (fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product Not Found with id: " + id);
        }
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCTS_" + id, fakeStoreProductDTO.getProduct());
        return fakeStoreProductDTO.getProduct();
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
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        Product deletedProduct = getSingleProduct(id);
        deletedProduct.setDeleted(true);
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
        return deletedProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, Category category, String imageUrl) throws ProductNotFoundException {
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
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) throws ProductNotFoundException {
        System.out.println("In the getAllProducts API in FKSPS");
        FakeStoreProductDTO[] fakeStoreListOfProducts  =
                restTemplate.getForObject("https://fakestoreapi.com/products/",
                        FakeStoreProductDTO[].class);
        if (fakeStoreListOfProducts  == null) {
            throw new ProductNotFoundException("No Products Found in the Database");
        }
        return (Page<Product>) new FakeStoreProductDTO().getListOfProducts(fakeStoreListOfProducts);
    }
}
