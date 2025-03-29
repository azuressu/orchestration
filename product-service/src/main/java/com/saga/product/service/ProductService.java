package com.saga.product.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.saga.product.model.dto.ProductRequest;
import com.saga.product.model.entity.Product;
import com.saga.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final RabbitTemplate rabbitTemplate;
	private final ProductRepository productRepository;

	@Value("${product.responseQueue}")
	private String productResponseQueue;

	public void processProduct(ProductRequest request) {
		String message;
		Product product = productRepository.findById(request.getProductId()).orElse(null);

		if (product == null || (product.getProductStock() < request.getQuantity())) {
			message = "fail";
		} else {
			message = "success";
		}

		// 처리 완료 후 Orchestrator로 응답 메시지 전송
		rabbitTemplate.convertAndSend(productResponseQueue, message + ":" + request.getOrderId());
	}


}
