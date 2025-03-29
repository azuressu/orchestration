package com.saga.orchestrator.listener;

import java.util.Arrays;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.orchestrator.service.OrchestratorService;

@Component
public class OrchestratorEventListener {

	private final OrchestratorService orchestratorService;

	public OrchestratorEventListener(OrchestratorService orchestratorService) {
		this.orchestratorService = orchestratorService;
	}

	@RabbitListener(queues = "${order.responseQueue}")
	public void onOrderResponse(String message) throws JsonProcessingException {
		System.out.println("주문 생성 후 넘어왔어요: " + message);
		String[] parts = message.split(":");
		String status = parts[0];
		Long orderId = parts[1].equals("null") ? null : Long.valueOf(parts[1]);
		Long productId = Long.valueOf(parts[2]);
		int productQuantity = Integer.parseInt(parts[3]);

		if (status.contains("success")) {
			System.out.println("주문 성공");
			orchestratorService.handleOrderSuccess(orderId, productId, productQuantity);
		} else {
			System.out.println("주문 실패");
			orchestratorService.handleOrderFailure();
		}
	}

	@RabbitListener(queues = "${product.responseQueue}")
	public void onProductResponse(String message) {
		System.out.println("Message received: " + message);

		// 메시지 분할
		String[] parts = message.split(":");

		if (parts.length == 2) {
			String orderIdStr = parts[1].trim(); // 공백 제거
			System.out.println("orderIdStr: " + orderIdStr);

			// orderIdStr을 출력해보고 실제 값이 무엇인지 확인
			try {
				Long orderId = Long.valueOf(orderIdStr); // 숫자로 변환
				System.out.println("상품 조회 성공, orderId: " + orderId);
				orchestratorService.handleProductSuccess(orderId);
			} catch (NumberFormatException e) {
				// 오류 발생 시, 어떤 값이 문제가 되는지 확인
				System.out.println("Invalid orderId format: " + orderIdStr);
				e.printStackTrace();
			}
		} else {
			System.out.println("Invalid message format: " + message);
		}
	}
}
