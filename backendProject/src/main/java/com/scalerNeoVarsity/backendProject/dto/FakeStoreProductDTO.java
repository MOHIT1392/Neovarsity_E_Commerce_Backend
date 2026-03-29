package com.scalerNeoVarsity.backendProject.dto;

import com.scalerNeoVarsity.backendProject.models.Category;
import com.scalerNeoVarsity.backendProject.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;

    //Used getters and setters because lombok was not working
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public List<Product> getListOfProducts(FakeStoreProductDTO[] fakeStoreListOfProducts) {
        List<Product> listOfProducts = new ArrayList<>();
        for (FakeStoreProductDTO fakeStoreListOfProduct : fakeStoreListOfProducts) {
            listOfProducts.add(fakeStoreListOfProduct.getProduct());
        }

        return listOfProducts;
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
}
