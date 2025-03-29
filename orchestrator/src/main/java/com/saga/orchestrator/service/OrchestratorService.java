package com.saga.orchestrator.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.orchestrator.model.OrderRequest;
import com.saga.orchestrator.messaging.OrchestratorMessageProducer;
import com.saga.orchestrator.model.ProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrchestratorService {  // Saga 실행 로직

	private final OrchestratorMessageProducer messageProducer;

	public void startOrchestrator(OrderRequest orderRequest) throws JsonProcessingException {
		messageProducer.sendOrderCreationMessage(orderRequest);
	}

	public void handleOrderSuccess(Long orderId, Long productId, Integer productQuantity) throws JsonProcessingException {
		// 2. 주문 성공 → 상품(Product) 처리 요청 전송
		System.out.println("주문 생성해서 상품으로 보낼거임");
		ProductRequest request = ProductRequest.builder().orderId(orderId).productId(productId).productQuantity(productQuantity).build();
		messageProducer.sendProductProcessingMessage(request);
	}

	public void handleOrderFailure() {
		// 주문 실패 처리 (필요 시 보상 처리)
		System.out.println("Order failed");
	}

	public void handleProductSuccess(Long orderId) {
		System.out.println("상품 조회 성공함");
		System.out.println("Product processing completed for order: " + orderId);
	}

	public void handleProductFailure(Long orderId) {
		System.out.println("상품 조회 실패함");
		messageProducer.sendOrderErrorMessage(orderId);
	}


}
