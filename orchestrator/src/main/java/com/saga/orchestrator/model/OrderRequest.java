package com.saga.orchestrator.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {

	private String orderId;
	private String productId;
	private int quantity;

}

