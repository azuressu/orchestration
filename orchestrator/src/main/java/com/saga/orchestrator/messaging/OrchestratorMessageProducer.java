package com.saga.orchestrator.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saga.orchestrator.model.OrderRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrchestratorMessageProducer {

	private final RabbitTemplate rabbitTemplate;

	@Value("${order.exchange}")
	private String orderExchange;

	@Value("${product.exchange}")
	private String productExchange;

	@Value("${order.routingKey}")
	private String orderRoutingKey;

	@Value("${product.routingKey}")
	private String productRoutingKey;

	public void sendOrderCreationMessage(OrderRequest orderRequest) {
		rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, orderRequest);
	}

	public void sendProductProcessingMessage(String orderId) {
		rabbitTemplate.convertAndSend(productExchange, productRoutingKey, "process:" + orderId);
	}
}
