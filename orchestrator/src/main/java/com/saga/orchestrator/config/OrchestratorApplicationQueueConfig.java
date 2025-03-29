package com.saga.orchestrator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrchestratorApplicationQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Order 관련 4가지 설정
    @Value("${order.exchange}")
    private String orderExchange;

    @Value("${order.queue}")
    private String orderQueue;

    @Value("${order.responseQueue}")
    private String orderResponseQueue;

    @Value("${order.routingKey}")
    private String orderRoutingKey;

    // Product 관련 4가지 설정
    @Value("${product.exchange}")
    private String productExchange;

    @Value("${product.queue}")
    private String productQueue;

    @Value("${product.responseQueue}")
    private String productResponseQueue;

    @Value("${product.routingKey}")
    private String productRoutingKey;

    @Value("${order.error.exchange}")
    private String orderErrorExchange;

    @Value("${order.error.queue}")
    private String orderErrorQueue;

    @Value("${order.error.routingKey}")
    private String orderErrorRoutingKey;

    // Order 관련 빈
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

    // Product 관련 빈
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

    // Order Error 관련 빈
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