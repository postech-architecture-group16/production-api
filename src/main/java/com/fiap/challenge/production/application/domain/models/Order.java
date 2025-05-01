package com.fiap.challenge.production.application.domain.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;

public class Order {

	private UUID id;
	
	private Long orderNumber;
	
	private OrderStatusEnum orderStatus;
	
	private BigDecimal total;
	
	private List<OrderProduct> products;
	
	public Order(UUID id, 
			Long orderNumber,
			OrderStatusEnum orderStatus,
			BigDecimal total,
			List<OrderProduct> products
			) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.total = total;
		this.products = products;
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

	public BigDecimal getTotal() {
		return total;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}
	

	
	

}
