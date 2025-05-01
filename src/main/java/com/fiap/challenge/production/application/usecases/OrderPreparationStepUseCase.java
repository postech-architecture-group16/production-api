package com.fiap.challenge.production.application.usecases;

import com.fiap.challenge.production.application.domain.models.Order;

public interface OrderPreparationStepUseCase {
	
	void receivedOrder(Order order);
	Order inPrepareOrder(Long orderNumber);
	Order readyOrder(Long orderNumber);
	Order finishOrder(Long orderNumber);
}
