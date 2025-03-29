package com.saga.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.saga.orchestrator.service.OrchestratorService;

@Component
public class OrchestratorEventListener {

	private final OrchestratorService orchestratorService;

	public OrchestratorEventListener(OrchestratorService orchestratorService) {
		this.orchestratorService = orchestratorService;
	}

	@RabbitListener(queues = "${order.responseQueue}")
	public void onOrderResponse(String message) {
		if (message.contains("success")) {
			orchestratorService.handleOrderSuccess(message.split(":")[1]);
		} else {
			orchestratorService.handleOrderFailure(message.split(":")[1]);
		}
	}

	@RabbitListener(queues = "${product.responseQueue}")
	public void onProductResponse(String message) {
		if (message.contains("success")) {
			orchestratorService.handleProductSuccess(message.split(":")[1]);
		} else {
			orchestratorService.handleProductFailure(message.split(":")[1]);
		}
	}
}
