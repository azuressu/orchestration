package com.saga.product.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.product.model.dto.ProductRequest;
import com.saga.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventListener {

	private final ProductService productService;
	private final ObjectMapper objectMapper;

	// Orchestrator에서 주문 생성 후 전달되는 상품 처리 메시지
	@RabbitListener(queues = "${product.queue}")
	public void onProductProcessing(String request) {
		System.out.println("상품에서 요청 받았어요 : " + request);
		try {
			// JSON을 ProductRequest 객체로 변환
			ProductRequest productRequest = objectMapper.readValue(request, ProductRequest.class);

			// 상품 처리 로직 실행
			productService.processProduct(productRequest);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}