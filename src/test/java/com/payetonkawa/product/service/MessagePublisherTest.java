package com.payetonkawa.product.service;

import com.paketonkawa.resources.message.Action;
import com.paketonkawa.resources.message.MessageDTO;
import com.paketonkawa.resources.message.Table;
import com.payetonkawa.product.config.RabbitConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessagePublisherTest {

    private RabbitTemplate rabbitTemplate;
    private MessagePublisher messagePublisher;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        messagePublisher = new MessagePublisher(rabbitTemplate);
    }

    @Test
    void sendMessage_shouldCallConvertAndSendWithCorrectParameters() {
        // Given
        String routingKey = "test.api";
        MessageDTO message = new MessageDTO(Action.INSERT, Table.PRODUCT, Map.of("productId", 1));

        // When
        messagePublisher.sendMessage(message, routingKey);

        // Then
        verify(rabbitTemplate).convertAndSend(RabbitConfig.EXCHANGE_NAME, routingKey, message);
    }

    @Test
    void sendMessage_shouldCatchExceptionAndLogIt() {
        // Given
        String routingKey = "test.api";
        MessageDTO message = new MessageDTO(Action.UPDATE, Table.PRODUCT, Map.of("productId", 2));
        doThrow(new RuntimeException("RabbitMQ failure")).when(rabbitTemplate)
                .convertAndSend(anyString(), anyString(), (Object) any());

        // When / Then
        assertDoesNotThrow(() -> messagePublisher.sendMessage(message, routingKey));
        verify(rabbitTemplate).convertAndSend(RabbitConfig.EXCHANGE_NAME, routingKey, message);
    }
}
