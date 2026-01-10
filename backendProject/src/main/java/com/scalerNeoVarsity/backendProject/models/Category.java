package com.scalerNeoVarsity.backendProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends BaseModel {
    private String title;

    //We declare the cardinality between the Product and the Category Class
    //We mention mappedBy here so that Hibernate
    // does not create another mapping on its own
    //Since in the Product class, this cardinality is mapped by the category attribute,
    //we mention that here to indicate Hibernate not to create another mapping
    //We also mention the fetch type b/w Eager and Lazy

    @JsonIgnore
    private List<Product> products;

    public Category(String testCategory) {
    }


    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                '}';
    }
}
