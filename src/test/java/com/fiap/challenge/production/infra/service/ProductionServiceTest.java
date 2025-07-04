package com.fiap.challenge.production.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.application.domain.models.OrderProduct;
import com.fiap.challenge.production.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.production.infra.database.entity.ProductionEntity;
import com.fiap.challenge.production.infra.database.repository.ProductionRepository;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private ProductionRepository productionRepository;

    @InjectMocks
    private ProductionService productionService;

    private Order mockOrder;
    private OrderProduct mockOrderProduct;
    private ProductionEntity mockEntity;

    @BeforeEach
    void setUp() {
    	mockOrderProduct = new OrderProduct();
    	mockOrderProduct.setProductId(UUID.randomUUID());
    	mockOrderProduct.setPrice(BigDecimal.valueOf(50.00));
    	mockOrderProduct.setProductName("Product A");
    	mockOrderProduct.setCreatedAt(LocalDateTime.now());
    	
    	OrderProduct mockOrderProduct2 = new OrderProduct(
    			mockOrderProduct.getProductId(), 
    			mockOrderProduct.getPrice(), 
    			mockOrderProduct.getProductName(), 
    			mockOrderProduct.getCreatedAt());
    	
        mockOrder =  new Order(
                UUID.randomUUID(),
                2L,
                OrderStatusEnum.EM_PREPARACAO,
                BigDecimal.valueOf(100.00),
                List.of(mockOrderProduct, mockOrderProduct2)
            );

        mockEntity = new ProductionEntity();
        mockEntity.setOrderId(mockOrder.getId());
        mockEntity.setOrderNumber(mockOrder.getOrderNumber());
        mockEntity.setOrderStatus(mockOrder.getOrderStatus());
        mockEntity.setTotal(mockOrder.getTotal());
        mockEntity.setProducts(mockOrder.getProducts());
    }

    @Test
    void receivedOrderShouldSaveEntity() {
        productionService.receivedOrder(mockOrder);

        verify(productionRepository, times(1)).save(any(ProductionEntity.class));
    }

    @Test
    void inPrepareOrderShouldUpdateStatusAndReturnOrder() {
        when(productionRepository.findByOrderNumber(123L)).thenReturn(mockEntity);
        when(productionRepository.save(mockEntity)).thenReturn(mockEntity);

        Order result = productionService.inPrepareOrder(123L);

        assertNotNull(result);
        assertEquals(OrderStatusEnum.EM_PREPARACAO, mockEntity.getOrderStatus());
        verify(productionRepository, times(1)).findByOrderNumber(123L);
        verify(productionRepository, times(1)).save(mockEntity);
    }

    @Test
    void readyOrderShouldUpdateStatusAndReturnOrder() {
        when(productionRepository.findByOrderNumber(123L)).thenReturn(mockEntity);
        when(productionRepository.save(mockEntity)).thenReturn(mockEntity);

        Order result = productionService.readyOrder(123L);

        assertNotNull(result);
        assertEquals(OrderStatusEnum.PRONTO, mockEntity.getOrderStatus());
        verify(productionRepository, times(1)).findByOrderNumber(123L);
        verify(productionRepository, times(1)).save(mockEntity);
    }

    @Test
    void finishOrderShouldUpdateStatusAndReturnOrder() {
        when(productionRepository.findByOrderNumber(123L)).thenReturn(mockEntity);
        when(productionRepository.save(mockEntity)).thenReturn(mockEntity);

        Order result = productionService.finishOrder(123L);

        assertNotNull(result);
        assertEquals(OrderStatusEnum.FINALIZADO, mockEntity.getOrderStatus());
        verify(productionRepository, times(1)).findByOrderNumber(123L);
        verify(productionRepository, times(1)).save(mockEntity);
    }
    
    @Test
    void shouldListOrdersPrepareAndReadySuccessfully() {
        List<ProductionEntity> mockEntities = List.of(
            new ProductionEntity(UUID.randomUUID(),UUID.randomUUID(), 1001L, OrderStatusEnum.EM_PREPARACAO, null, null, LocalDateTime.now(),LocalDateTime.now()),
            new ProductionEntity(UUID.randomUUID(),UUID.randomUUID(), 1002L, OrderStatusEnum.EM_PREPARACAO, null, null, LocalDateTime.now(),LocalDateTime.now()),
            new ProductionEntity(UUID.randomUUID(),UUID.randomUUID(), 1003L, OrderStatusEnum.PRONTO, null, null, LocalDateTime.now(), LocalDateTime.now())
        );

        Mockito.when(productionRepository.findOrdersPrepareAndReady()).thenReturn(mockEntities);

        Map<OrderStatusEnum, List<Long>> result = productionService.listOrdersPrepareAndReady();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(OrderStatusEnum.EM_PREPARACAO).size());
        Assertions.assertEquals(1, result.get(OrderStatusEnum.PRONTO).size());
        Assertions.assertTrue(result.get(OrderStatusEnum.EM_PREPARACAO).contains(1001L));
        Assertions.assertTrue(result.get(OrderStatusEnum.EM_PREPARACAO).contains(1002L));
        Assertions.assertTrue(result.get(OrderStatusEnum.PRONTO).contains(1003L));
    }
    
}