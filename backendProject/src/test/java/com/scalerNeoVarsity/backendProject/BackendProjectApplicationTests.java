package com.scalerNeoVarsity.backendProject;

import com.scalerNeoVarsity.backendProject.models.Product;
import com.scalerNeoVarsity.backendProject.repository.ProductRepository;
import com.scalerNeoVarsity.backendProject.repository.projections.ProductProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendProjectApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void contextLoads() {
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