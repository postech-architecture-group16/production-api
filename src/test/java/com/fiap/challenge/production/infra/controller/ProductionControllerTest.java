package com.fiap.challenge.production.infra.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    void shouldReceiveOrderSuccessfully() {
        Order order = new Order(
            UUID.randomUUID(),
            2L,
            OrderStatusEnum.EM_PREPARACAO,
            BigDecimal.valueOf(100.00),
            List.of(new OrderProduct(UUID.randomUUID(), BigDecimal.valueOf(50.00), "Product A", LocalDateTime.now()))
        );

        Mockito.when(productionService.inPrepareOrder(2L)).thenReturn(order);
        
		ResponseEntity<Order> response = productionController.inPrepareOrder(2L);
        
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(OrderStatusEnum.EM_PREPARACAO, response.getBody().getOrderStatus());
    }
    
    @Test
    void shouldReadyOrderSuccessfully()  {
        Order order = new Order(
            UUID.randomUUID(),
            2L,
            OrderStatusEnum.PRONTO,
            BigDecimal.valueOf(100.00),
            List.of(new OrderProduct(UUID.randomUUID(), BigDecimal.valueOf(50.00), "Product A", LocalDateTime.now()))
        );
        
        Mockito.when(productionService.readyOrder(2L)).thenReturn(order);
        
        ResponseEntity<Order> response = productionController.readyOrder(2L);
        
    	Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(OrderStatusEnum.PRONTO, order.getOrderStatus());
    }
    
    @Test
    void shouldFinishedOrderSuccessfully() {
        Order order = new Order(
            UUID.randomUUID(),
            2L,
            OrderStatusEnum.FINALIZADO,
            BigDecimal.valueOf(100.00),
            List.of(new OrderProduct(UUID.randomUUID(), BigDecimal.valueOf(50.00), "Product A", LocalDateTime.now()))
        );

        Mockito.when(productionService.finishOrder(2L)).thenReturn(order);
        
        ResponseEntity<Order> response = productionController.finishOrder(2L);
        
    	Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(OrderStatusEnum.FINALIZADO, order.getOrderStatus());
    }
    
    @Test
    void shouldListOrdersPrepareAndReadySuccessfully() {
        // Mock data
        Map<OrderStatusEnum, List<Long>> mockResponse = Map.of(
            OrderStatusEnum.EM_PREPARACAO, List.of(1L, 2L),
            OrderStatusEnum.PRONTO, List.of(3L)
        );

        // Mock service behavior
        Mockito.when(productionService.listOrdersPrepareAndReady()).thenReturn(mockResponse);

        // Call the controller method
        ResponseEntity<Map<OrderStatusEnum, List<Long>>> response = productionController.getOrders();

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().get(OrderStatusEnum.EM_PREPARACAO).size());
        Assertions.assertEquals(1, response.getBody().get(OrderStatusEnum.PRONTO).size());
        Assertions.assertTrue(response.getBody().get(OrderStatusEnum.EM_PREPARACAO).contains(1L));
        Assertions.assertTrue(response.getBody().get(OrderStatusEnum.EM_PREPARACAO).contains(2L));
        Assertions.assertTrue(response.getBody().get(OrderStatusEnum.PRONTO).contains(3L));
    }

}
