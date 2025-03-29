package com.saga.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

	@Value("${order.queue}")
	private String orderQueue;

	@Value("${order.responseQueue}")
	private String orderResponseQueue;

	@Value("${order.exchange}")
	private String orderExchange;

	@Value("${order.routingKey}")
	private String orderRoutingKey;

	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(orderExchange);
	}

	@Bean
	public Queue orderQueue() {
		return new Queue(orderQueue);
	}

	@Bean
	public Queue orderResponseQueue() {
		return new Queue(orderResponseQueue);
	}

	@Bean
	public Binding bindOrderQueue() {
		return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(orderRoutingKey);
	}

	@Bean
	public Binding bindOrderResponseQueue() {
		return BindingBuilder.bind(orderResponseQueue()).to(orderExchange()).with(orderRoutingKey + ".response");
	}


}
