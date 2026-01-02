package com.scalerNeoVarsity.backendProject.dto;

import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;

    //This will get the product of my implementation
    //using the values from Fake Store
    public Product getProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        //Used cat, as "Category" was already used as attribute
        Category cat = new Category();
        cat.setTitle(category);
        product.setCategory(cat);

        return product;
    }
    @Override
    public String toString() {
        return "FakeStoreProductDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

        public List<Product> getListOfProducts(FakeStoreProductDTO[] fakeStoreListOfProducts) {
            List<Product> listOfProducts = new ArrayList<>();
            for (FakeStoreProductDTO fakeStoreListOfProduct : fakeStoreListOfProducts) {
                listOfProducts.add(fakeStoreListOfProduct.getProduct());
        }

        return listOfProducts;
    }


}