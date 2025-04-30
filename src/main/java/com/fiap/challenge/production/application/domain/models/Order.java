package com.fiap.challenge.production.application.domain.models;

import java.util.UUID;

import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;

public class Order {

	private UUID id;
	
	private Long orderNumber;
	
	private OrderStatusEnum orderStatus;
	
	public Order(UUID id, 
			Long orderNumber,
			OrderStatusEnum orderStatus
			) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
	}
	
	public UUID getId() {
		return id;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}
	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	
	

}
