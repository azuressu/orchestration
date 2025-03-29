package com.saga.order.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.saga.order.model.dto.OrderRequest;
import com.saga.order.model.entity.Order;
import com.saga.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService {

	private final RabbitTemplate rabbitTemplate;
	private final OrderRepository orderRepository;

	@Value("${order.responseQueue}")
	private String orderResponseQueue;

	public void processOrder(OrderRequest request) {
		Long orderId;
		String message;

		try {
			Order order = Order.builder().productId(request.getProductId()).productQuantity(request.getProductQuantity()).build();
			orderRepository.save(order);
			message = "success";
			orderId = order.getOrderId();
		} catch (Exception e) {
			message = "fail";
			orderId = null;
		}

		// 처리 완료 후 Orchestrator로 응답 메시지 전송
		rabbitTemplate.convertAndSend(orderResponseQueue, message + ":" + orderId);
	}

	public void cancelOrderByProduct(Long orderId) {
		// 상품 측에서의 오류, 재고 부족 등으로 인한 주문 생성 취소 로직
		Order order = orderRepository.findById(orderId).orElse(null);

		if (order != null) {
			orderRepository.delete(order);
		}
	}

}
