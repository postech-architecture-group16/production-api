package com.fiap.challenge.production.application.usecases;

import java.util.List;
import java.util.Map;

import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;

public interface ListOrdersPrepareAndReadyUseCase {

	Map<OrderStatusEnum, List<Long>> listOrdersPrepareAndReady();
	
}
