package com.fiap.challenge.production.infra.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.production.infra.database.service.ProductionService;
import com.fiap.challenge.production.infra.models.dto.OrderDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqListener {

	
	private ObjectMapper objectMapper;
	
	private ProductionService productionService;
	
	public MqListener(ProductionService productionService) {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		this.productionService = productionService;
	}

	@RabbitListener(queues = {"${queue.name.listener}"})
	public void receive(@Payload String message) throws JsonMappingException, JsonProcessingException {
		OrderDTO orderDTO = objectMapper.readValue(message, OrderDTO.class);
		Order order = new Order(orderDTO.id(), 
				orderDTO.orderNumber(),
				OrderStatusEnum.RECEBIDO);
		log.info("Order received: {}", order.toString());
		productionService.receivedOrder(order);
	}
	
}
