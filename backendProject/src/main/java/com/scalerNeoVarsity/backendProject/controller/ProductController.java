package com.scalerNeoVarsity.backendProject.controller;

import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    //CRUD APIs around Controller
    /*For the product
    * 1. Create a product
    * 2. Get a product
    * 3. Update a product
    * 4. Delete a product
    */

    //This product controller has now the dependency on the productService
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //This will help in performing "Create" function
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        //Creating new object of Product to pass the object in response entity
        Product newCreatedProduct = productService.createProduct(product.getId(),
                product.getTitle(), product.getDescription(),
                product.getPrice(), product.getCategory().getTitle(),
                product.getImageUrl());

        //Local variable 'productResponseEntity' is redundant
        return new ResponseEntity<>(
                newCreatedProduct,
                HttpStatus.CREATED
        );
    }

    //This will help in "Retrieve" function
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        System.out.println("Starting the getSingleProduct API here");
        Product product = productService.getSingleProduct(id);
        System.out.println("Ending the getSingleProduct API here");

        //First argument is the data we want to pass
        //Next is the HttpStatus Class code
        //Local variable 'productResponseEntity' is redundant

        return new ResponseEntity<>(
                product,              //First argument is the data we want to pass
                HttpStatus.OK   //Next is the HttpStatus Class code
        );
    }



    //This will help in "Update" function
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id,
                product.getTitle(), product.getDescription(),
                product.getPrice(), product.getCategory(),
                product.getImageUrl());

        //Local variable 'updatedProductResponseEntity' was redundant
        return new ResponseEntity<>(
                updatedProduct,
                HttpStatus.OK
        );
    }

    //This will help in "Delete" function
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        System.out.println("Starting the delete API");
        Product deletedProduct = productService.deleteProduct(id);
        System.out.println("Ending the delete API");

        return new ResponseEntity<>(
                deletedProduct,
                HttpStatus.ACCEPTED

        );
    }

    @GetMapping("/product")
    public List<Product> getAllProducts() {
        System.out.println("Starting the Get all Products API");
        List<Product> products = productService.getAllProducts();
        System.out.println("Ending the Get All products API");

        return products;
    }

}
