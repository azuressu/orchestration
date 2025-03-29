package com.saga.order.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.saga.order.model.dto.OrderRequest;
import com.saga.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final OrderService orderService;

	@RabbitListener(queues = "${order.queue}")
	public void createOrder(OrderRequest request) {
		// 주문 생성 처리
		orderService.processOrder(request);
	}

}
