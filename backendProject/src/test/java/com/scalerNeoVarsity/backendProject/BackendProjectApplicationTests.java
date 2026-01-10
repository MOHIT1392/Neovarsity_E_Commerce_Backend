package com.scalerNeoVarsity.backendProject;

import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.repository.ProductRepository;
import com.scalerNeoVarsity.backendProject.repository.projections.ProductProjection;
import org.junit.jupiter.api.Test;
import com.scalerNeoVarsity.backendProject.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.scalerNeoVarsity.backendProject.repository.CategoryRepository;

import java.util.List;

@SpringBootTest
class BackendProjectApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
	}


	@Test
	void fetchTypeTest() {
		Category category = categoryRepository.findById(1L);
		System.out.println(category.getId());
		System.out.println("We are done here");

		List<Product> currentProducts = category.getProducts();
		System.out.println(currentProducts.size());

		System.out.println("We have the list of the products");
	}

	@Test
	void testQueries() {
		List<Product> allProducts = productRepository.getProductByCategoryId(1L);

		for (Product product : allProducts) {
			System.out.println(product.toString());
		}
	}

	@Test
	void testNativeQueries() {
		List<Product> allProducts = productRepository.getProductByCategoryIdByNativeQuery(1L);

		for (Product product : allProducts) {
			System.out.println(product.toString());
		}
	}

	@Test
	void testProjections() {
		List<ProductProjection> productProjectionList = productRepository.getProductByCategoryIdUsingProjections(1L);
		System.out.println(productProjectionList.get(0).getTitle());
	}

}