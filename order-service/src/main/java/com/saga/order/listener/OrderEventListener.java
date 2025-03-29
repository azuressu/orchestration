package com.saga.order.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.order.model.dto.OrderRequest;
import com.saga.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final OrderService orderService;
	private final ObjectMapper objectMapper;

	@RabbitListener(queues = "${order.queue}")
	public void createOrder(String request) {
		try {
			// JSON을 OrderRequest 객체로 변환
			OrderRequest orderRequest = objectMapper.readValue(request, OrderRequest.class);

			// 주문 처리 로직 실행
			orderService.processOrder(orderRequest);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@RabbitListener(queues = "${order.error.queue}")
	public void cancelOrder(Long orderId) {
		orderService.cancelOrderByProduct(orderId);
	}

}
