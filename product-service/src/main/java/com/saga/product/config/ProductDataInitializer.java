package com.saga.product.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import com.saga.product.model.entity.Product;
import com.saga.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDataInitializer implements CommandLineRunner {

	private final ProductRepository productRepository;
	@Override
	public void run(String... args) throws Exception {
		if (productRepository.count() == 0) {
			Product product1 = Product.builder().productName("책상").productStock(1500).build();
			Product product2 = Product.builder().productName("의자").productStock(2000).build();
			Product product3 = Product.builder().productName("스탠드").productStock(3000).build();

			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
		}
	}
}
