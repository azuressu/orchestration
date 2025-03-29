package com.saga.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saga.order.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
