package com.saga.product.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.saga.product.model.dto.ProductRequest;
import com.saga.product.service.ProductService;

@Component
public class ProductEventListener {

	private final ProductService productService;

	public ProductEventListener(ProductService productService) {
		this.productService = productService;
	}

	// Orchestrator에서 주문 생성 후 전달되는 상품 처리 메시지
	@RabbitListener(queues = "${product.queue}")
	public void onProductProcessing(ProductRequest productRequest) {
		// 상품 처리 로직 실행
		productService.processProduct(productRequest);
	}
}