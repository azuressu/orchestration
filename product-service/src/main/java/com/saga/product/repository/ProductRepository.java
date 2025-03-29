package com.saga.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saga.product.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
