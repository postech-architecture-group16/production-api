package com.fiap.challenge.production.application.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderProduct {
	
	private UUID productId;

	private BigDecimal price;
	
	private String productName;
	
	private LocalDateTime createdAt;

	public OrderProduct() {
	}
	
	public OrderProduct( 
			UUID productId, 
			BigDecimal price, 
			String productName,
			LocalDateTime createdAt) {
		this.productId = productId;
		this.price = price;
		this.productName = productName;
		this.createdAt = createdAt;
	}


	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
