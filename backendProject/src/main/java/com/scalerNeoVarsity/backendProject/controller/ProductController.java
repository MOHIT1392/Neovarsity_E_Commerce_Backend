package com.scalerNeoVarsity.backendProject.controller;

import com.scalerNeoVarsity.backendProject.dto.ErrorDTO;
import com.scalerNeoVarsity.backendProject.exception.ProductNotFoundException;
import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    //CRUD APIs around Controller
    /*For the product
     * 1. Create a product
     * 2. Get a product
     * 3. Update a product
     * 4. Delete a product
     */

    private final ProductService productService;


    public ProductController(@Qualifier("selfProductService") ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
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


    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        System.out.println("Starting the getSingleProduct API here");
        Product p = productService.getSingleProduct(id);
        System.out.println("Ending the API here");



        return new ResponseEntity<>(
                p,              //First argument is the data we want to pass
                HttpStatus.OK   //Next is the HttpStatus Class code
        );
    }

//    @GetMapping("/products")
//    public ResponseEntity<List<Product>> getAllProducts() {
//        System.out.println("Starting the Get all Products API here");
//        List<Product> productList = null;
//        try {
//            productList = productService.getAllProducts();
//        } catch (ProductNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Ending the Get All products API");
//
//        //Local variable 'productResponseEntity' is redundant
//        //Added response entity for all products as well
//        return new ResponseEntity<>(
//                productList,
//                HttpStatus.OK
//        );
//    }
//


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("fieldName") String fieldName
    ) {
        Page<Product> productList = null;
        try {
            productList = productService.getAllProducts(pageNumber, pageSize, fieldName);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(
                productList,
                HttpStatus.OK
        );
    }


    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        Product updatedProduct = productService.updateProduct(id,
                product.getTitle(), product.getDescription(),
                product.getPrice(), product.getCategory(),
                product.getImageUrl());

        return new ResponseEntity<>(
                updatedProduct,
                HttpStatus.OK
        );
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        System.out.println("Starting the delete API");
        Product deletedProduct = productService.deleteProduct(id);
        System.out.println("Ending the delete API");


        return new ResponseEntity<>(
                deletedProduct,
                HttpStatus.ACCEPTED

        );
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        return new ResponseEntity<>(
                errorDTO,
                HttpStatus.NOT_FOUND
        );
    }
}