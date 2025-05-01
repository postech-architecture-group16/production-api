package com.fiap.challenge.production.infra.mq;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.challenge.production.application.domain.models.Order;
import com.fiap.challenge.production.infra.models.dto.OrderDTO;
import com.fiap.challenge.production.infra.service.ProductionService;

@ExtendWith(MockitoExtension.class)
class MqListenerTest {

    @Mock
    private ProductionService productionService;

    @InjectMocks
    private MqListener mqListener;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void receiveShouldCallProductionServiceWithValidOrder() throws JsonProcessingException {
        OrderDTO orderDTO = new OrderDTO(
            UUID.randomUUID(),
            123L,
            BigDecimal.valueOf(100.00),
            Collections.emptyList()
        );
        String message = objectMapper.writeValueAsString(orderDTO);

        mqListener.receive(message);

        verify(productionService, times(1)).receivedOrder(any(Order.class));
    }

    @Test
    void receiveShouldThrowJsonProcessingExceptionForInvalidMessage() {
        String invalidMessage = "invalid-json";

        Assertions.assertThrows(
            JsonProcessingException.class,
            () -> mqListener.receive(invalidMessage)
        );

        verify(productionService, never()).receivedOrder(any(Order.class));
    }
}