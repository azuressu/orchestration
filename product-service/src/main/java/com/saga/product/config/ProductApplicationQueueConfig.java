package com.saga.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductApplicationQueueConfig {

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Value("${product.exchange}")
	private String productExchange;

	@Value("${product.queue}")
	private String productQueue;

	@Value("${product.responseQueue}")
	private String productResponseQueue;

	@Value("${product.routingKey}")
	private String productRoutingKey;

	@Bean
	public TopicExchange productExchange() {
		return new TopicExchange(productExchange);
	}

	@Bean
	public Queue productQueue() {
		return new Queue(productQueue);
	}

	@Bean
	public Queue productResponseQueue() {
		return new Queue(productResponseQueue);
	}

	@Bean
	public Binding bindProductQueue() {
		return BindingBuilder.bind(productQueue()).to(productExchange()).with(productRoutingKey + ".response");
	}

	@Bean
	public Binding bindProductResponseQueue() {
		return BindingBuilder.bind(productResponseQueue()).to(productExchange()).with(productRoutingKey + ".response");
	}
}
