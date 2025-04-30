package com.fiap.challenge.production.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.production.infra.database.service.ProductionService;

@RestController
@RequestMapping("/production/api")
public class ProductionController {
	
	private ProductionService productionService;
	
	public ProductionController(@Autowired ProductionService productionService) {
		this.productionService = productionService;
	}

	@PatchMapping("/order-prepare/{orderNumber}")
	public void inPrepareOrder(@PathVariable Long orderNumber) {
		productionService.inPrepareOrder(orderNumber);
	}
	
	@PatchMapping("/order-ready/{orderNumber}")
	public void readyOrder(@PathVariable Long orderNumber) {
		productionService.readyOrder(orderNumber);
	}
	
	@PatchMapping("/order-finish/{orderNumber}")
	public void finishOrder(@PathVariable Long orderNumber) {
		productionService.finishOrder(orderNumber);
	}

}
