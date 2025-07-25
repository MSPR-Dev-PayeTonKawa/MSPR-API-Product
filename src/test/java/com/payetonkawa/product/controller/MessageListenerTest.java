package com.payetonkawa.product.controller;

import com.paketonkawa.resources.message.Action;
import com.paketonkawa.resources.message.MessageDTO;
import com.paketonkawa.resources.message.Table;
import com.payetonkawa.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class MessageListenerTest {

    private ProductService productService;
    private MessageListener messageListener;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        messageListener = new MessageListener(productService);
    }

    @Test
    void receiveMessage_shouldHandleInsertOrderDetail() {
        Map<String, Object> info = new HashMap<>();
        info.put("productId", 1);
        info.put("quantity", 5);

        MessageDTO message = new MessageDTO(Action.INSERT, Table.ORDER_DETAIL, info);

        messageListener.receiveMessage(message);

        verify(productService).updateQuantityById(1, 5);
    }

    @Test
    void receiveMessage_shouldHandleUpdateOrderDetail() {
        Map<String, Object> info = new HashMap<>();
        info.put("productId", 2);
        info.put("oldQuantity", 3);
        info.put("newQuantity", 6);

        MessageDTO message = new MessageDTO(Action.UPDATE, Table.ORDER_DETAIL, info);

        messageListener.receiveMessage(message);

        verify(productService).updateQuantityById(2, 3); // 6 - 3
    }

    @Test
    void receiveMessage_shouldHandleDeleteOrderDetail_whenCanceled() {
        Map<String, Object> info = new HashMap<>();
        info.put("productId", 3);
        info.put("quantity", 4);
        info.put("canceled", true);

        MessageDTO message = new MessageDTO(Action.DELETE, Table.ORDER_DETAIL, info);

        messageListener.receiveMessage(message);

        verify(productService).updateQuantityById(3, 4);
    }

    @Test
    void receiveMessage_shouldIgnoreDeleteOrderDetail_whenNotCanceled() {
        Map<String, Object> info = new HashMap<>();
        info.put("productId", 3);
        info.put("quantity", 4);
        info.put("canceled", false);

        MessageDTO message = new MessageDTO(Action.DELETE, Table.ORDER_DETAIL, info);

        messageListener.receiveMessage(message);

        verifyNoInteractions(productService);
    }

    @Test
    void receiveMessage_shouldIgnoreUnrelatedMessage() {
        Map<String, Object> info = new HashMap<>();
        info.put("clientId", 99);

        MessageDTO message = new MessageDTO(Action.UPDATE, Table.CLIENT, info);

        messageListener.receiveMessage(message);

        verifyNoInteractions(productService);
    }
}
