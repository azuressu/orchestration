package com.saga.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

	private Long orderId; // 다시 send 해줘야해서 필요하긴 함
	private Long productId;
	private Integer quantity;

}
