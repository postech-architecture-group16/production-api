package com.fiap.challenge.production.infra.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.application.domain.models.OrderProduct;
import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.production.infra.service.ProductionService;

@ExtendWith(MockitoExtension.class)
class ProductionControllerTest {


    @InjectMocks
    private ProductionController productionController;
    
    @Mock
    private ProductionService productionService;


    @Test
    void shouldReceiveOrderSuccessfully() throws Exception {
        Order order = new Order(
            UUID.randomUUID(),
            2L,
            OrderStatusEnum.EM_PREPARACAO,
            BigDecimal.valueOf(100.00),
            List.of(new OrderProduct(UUID.randomUUID(), BigDecimal.valueOf(50.00), "Product A", LocalDateTime.now()))
        );

        Mockito.when(productionService.inPrepareOrder(2L)).thenReturn(order);
        
		ResponseEntity<Order> response = productionController.inPrepareOrder(2L);
        
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().getOrderStatus(), OrderStatusEnum.EM_PREPARACAO);
    }
    
    @Test
    void shouldReadyOrderSuccessfully() throws Exception {
        Order order = new Order(
            UUID.randomUUID(),
            2L,
            OrderStatusEnum.PRONTO,
            BigDecimal.valueOf(100.00),
            List.of(new OrderProduct(UUID.randomUUID(), BigDecimal.valueOf(50.00), "Product A", LocalDateTime.now()))
        );
        
        Mockito.when(productionService.readyOrder(2L)).thenReturn(order);
        
        ResponseEntity<Order> response = productionController.readyOrder(2L);
        
    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(order.getOrderStatus(), OrderStatusEnum.PRONTO);
    }
    
    @Test
    void shouldFinishedOrderSuccessfully() throws Exception {
        Order order = new Order(
            UUID.randomUUID(),
            2L,
            OrderStatusEnum.FINALIZADO,
            BigDecimal.valueOf(100.00),
            List.of(new OrderProduct(UUID.randomUUID(), BigDecimal.valueOf(50.00), "Product A", LocalDateTime.now()))
        );

        Mockito.when(productionService.finishOrder(2L)).thenReturn(order);
        
        ResponseEntity<Order> response = productionController.finishOrder(2L);
        
    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(order.getOrderStatus(), OrderStatusEnum.FINALIZADO);
    }

}
