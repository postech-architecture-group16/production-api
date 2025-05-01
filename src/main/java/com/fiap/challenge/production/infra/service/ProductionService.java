package com.fiap.challenge.production.infra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.production.application.usecases.OrderPreparationStepUseCase;
import com.fiap.challenge.production.infra.database.entity.ProductionEntity;
import com.fiap.challenge.production.infra.database.repository.ProductionRepository;

@Service
public class ProductionService implements OrderPreparationStepUseCase{

	private ProductionRepository productionRepository;
	
	public ProductionService(@Autowired ProductionRepository productionRepository) {
		this.productionRepository = productionRepository;
	}
	
	@Override
	public void receivedOrder(Order order) {
		ProductionEntity productionEntity = new ProductionEntity();
		productionEntity.setOrderId(order.getId());
		productionEntity.setOrderNumber(order.getOrderNumber());
		productionEntity.setOrderStatus(order.getOrderStatus());
		productionEntity.setTotal(order.getTotal());
		productionEntity.setProducts(order.getProducts());
		productionRepository.save(productionEntity);
	}
	@Override
	public Order inPrepareOrder(Long orderNumber) {
		ProductionEntity productionEntity = productionRepository.findByOrderNumber(orderNumber);
			productionEntity.setOrderStatus(OrderStatusEnum.EM_PREPARACAO);
		return productionRepository.save(productionEntity).toOrder();
	}

	@Override
	public Order readyOrder(Long orderNumber) {
		ProductionEntity productionEntity = productionRepository.findByOrderNumber(orderNumber);
			productionEntity.setOrderStatus(OrderStatusEnum.PRONTO);
			return productionRepository.save(productionEntity).toOrder();
	}

	@Override
	public Order finishOrder(Long orderNumber) {
		ProductionEntity productionEntity = productionRepository.findByOrderNumber(orderNumber);
			productionEntity.setOrderStatus(OrderStatusEnum.FINALIZADO);
			return productionRepository.save(productionEntity).toOrder();
	}

}
