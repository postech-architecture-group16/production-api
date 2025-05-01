package com.fiap.challenge.production.infra.database.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.application.domain.models.OrderProduct;
import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "production")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "order_id")
	private UUID orderId;
	
	@Column(name = "order_number")
	private Long orderNumber;
	
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatus;
	
	@Column(name = "total")
	private BigDecimal total;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "products", columnDefinition = "json")
	private List<OrderProduct> products;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public Order toOrder() {
		return new Order(
				this.orderId,
				this.orderNumber,
				this.orderStatus,
				this.total,
				this.products);
	}
	
}
