package com.fiap.challenge.production.application.usecases;

import com.fiap.challenge.production.application.domain.models.Order;

public interface OrderPreparationStepUseCase {
	
	void receivedOrder(Order order);
	void inPrepareOrder(Long orderNumber);
	void readyOrder(Long orderNumber);
	void finishOrder(Long orderNumber);
}
