package com.saga.orchestrator.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.orchestrator.model.OrderRequest;
import com.saga.orchestrator.model.ProductRequest;

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

	@Value("${order.error.exchange}")
	private String orderErrorExchange;

	@Value("${order.error.routingKey}")
	private String orderErrorRoutingKey;

	public void sendOrderCreationMessage(OrderRequest orderRequest) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String request = objectMapper.writeValueAsString(orderRequest);

		rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, request);
	}

	public void sendProductProcessingMessage(ProductRequest request) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String message = objectMapper.writeValueAsString(request);
			System.out.println("Sending message: " + message); // 로그로 확인
			rabbitTemplate.convertAndSend(productExchange, productRoutingKey + ".response", message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void sendOrderErrorMessage(Long orderId) {
		rabbitTemplate.convertAndSend(orderErrorExchange, orderErrorRoutingKey, orderId);
	}
}
