package com.saga.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Value("${order.queue}")
	private String orderQueue;

	@Value("${order.responseQueue}")
	private String orderResponseQueue;

	@Value("${order.exchange}")
	private String orderExchange;

	@Value("${order.routingKey}")
	private String orderRoutingKey;

	@Value("${order.error.exchange}")
	private String orderErrorExchange;

	@Value("${order.error.queue}")
	private String orderErrorQueue;

	@Value("${order.error.routingKey}")
	private String orderErrorRoutingKey;

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

	@Bean
	public TopicExchange orderErrorExchange() {
		return new TopicExchange(orderErrorExchange);
	}

	@Bean
	public Queue orderErrorQueue() {
		return new Queue(orderErrorQueue);
	}

	@Bean
	public Binding bindOrderErrorQueue() {
		return BindingBuilder.bind(orderErrorQueue()).to(orderErrorExchange()).with(orderErrorRoutingKey);
	}

}
