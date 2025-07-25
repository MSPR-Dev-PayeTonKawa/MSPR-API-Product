package com.payetonkawa.product.controller;

import com.paketonkawa.resources.message.Action;
import com.paketonkawa.resources.message.MessageDTO;
import com.paketonkawa.resources.message.Table;
import com.payetonkawa.product.config.RabbitConfig;
import com.payetonkawa.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
public class MessageListener {
    private ProductService productService;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(MessageDTO message) {
        // Update the stock when an Order detail is created
        if(message.getAction() == Action.INSERT &&
                message.getTable() == Table.ORDER_DETAIL &&
                message.getInformation().get("productId") instanceof Integer productId &&
                message.getInformation().get("quantity") instanceof Integer quantity){
            productService.updateQuantityById(productId, quantity);
        }
        // Update the stock when an Order detail is created
        if(message.getAction() == Action.UPDATE &&
                message.getTable() == Table.ORDER_DETAIL &&
                message.getInformation().get("productId") instanceof Integer productId &&
                message.getInformation().get("oldQuantity") instanceof Integer oldQuantity &&
                message.getInformation().get("newQuantity") instanceof Integer newQuantity){
            productService.updateQuantityById(productId, newQuantity - oldQuantity);
        }
        // Update the stock when an Order detail is deleted if it was canceled
        if(message.getAction() == Action.DELETE &&
                message.getTable() == Table.ORDER_DETAIL &&
                message.getInformation().get("productId") instanceof Integer productId &&
                message.getInformation().get("quantity") instanceof Integer quantity &&
                message.getInformation().get("canceled") instanceof Boolean canceled &&
                canceled){
            productService.updateQuantityById(productId, quantity);
        }
    }
}
