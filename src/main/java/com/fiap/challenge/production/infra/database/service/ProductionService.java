package com.fiap.challenge.production.infra.database.service;

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
		productionRepository.save(productionEntity);
	}
	@Override
	public void inPrepareOrder(Long orderNumber) {
		ProductionEntity productionEntity = productionRepository.findByOrderNumber(orderNumber);
		if (productionEntity != null) {
			productionEntity.setOrderStatus(OrderStatusEnum.EM_PREPARACAO);
			productionRepository.save(productionEntity);
		}
	}

	@Override
	public void readyOrder(Long orderNumber) {
		ProductionEntity productionEntity = productionRepository.findByOrderNumber(orderNumber);
		if (productionEntity != null) {
			productionEntity.setOrderStatus(OrderStatusEnum.PRONTO);
			productionRepository.save(productionEntity);
		}
	}

	@Override
	public void finishOrder(Long orderNumber) {
		ProductionEntity productionEntity = productionRepository.findByOrderNumber(orderNumber);
		if (productionEntity != null) {
			productionEntity.setOrderStatus(OrderStatusEnum.FINALIZADO);
			productionRepository.save(productionEntity);
		}
	}

}
