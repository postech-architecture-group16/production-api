package com.fiap.challenge.production.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.infra.service.ProductionService;

@RestController
@RequestMapping("/production/api")
public class ProductionController {
	
	private ProductionService productionService;
	
	public ProductionController(@Autowired ProductionService productionService) {
		this.productionService = productionService;
	}

	@PatchMapping("/order-prepare/{orderNumber}")
	public ResponseEntity<Order> inPrepareOrder(@PathVariable Long orderNumber) {
		Order orderResponse = productionService.inPrepareOrder(orderNumber);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(orderResponse);
	}
	
	@PatchMapping("/order-ready/{orderNumber}")
	public ResponseEntity<Order> readyOrder(@PathVariable Long orderNumber) {
		Order orderResponse = productionService.readyOrder(orderNumber);
		return ResponseEntity.status(HttpStatus.OK)
				.body(orderResponse);
	}
	
	@PatchMapping("/order-finish/{orderNumber}")
	public ResponseEntity<Order> finishOrder(@PathVariable Long orderNumber) {
		Order orderResponse = productionService.finishOrder(orderNumber);
		return ResponseEntity.status(HttpStatus.OK)
				.body(orderResponse);
	}

}
