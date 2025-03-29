package com.saga.orchestrator.service;

import org.springframework.stereotype.Service;

import com.saga.orchestrator.model.OrderRequest;
import com.saga.orchestrator.messaging.OrchestratorMessageProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrchestratorService {  // Saga 실행 로직

	private final OrchestratorMessageProducer messageProducer;

	public void startOrchestrator(OrderRequest orderRequest) {
		messageProducer.sendOrderCreationMessage(orderRequest);
	}

	public void handleOrderSuccess(String orderId) {
		// 2. 주문 성공 → 상품(Product) 처리 요청 전송
		messageProducer.sendProductProcessingMessage(orderId);
	}

	public void handleOrderFailure(String orderId) {
		// 주문 실패 처리 (필요 시 보상 처리)
		System.out.println("Order failed: " + orderId);
	}

	public void handleProductSuccess(String orderId) {
		System.out.println("Product processing completed for order: " + orderId);
	}

	public void handleProductFailure(String orderId) {
		System.out.println("Product processing failed for order: " + orderId);
	}


}
