package com.fiap.challenge.production.infra.models.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.challenge.production.application.domain.models.OrderProduct;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderDTO(UUID id, 
		Long orderNumber,
		BigDecimal total,
		List<OrderProduct> products) {
}
