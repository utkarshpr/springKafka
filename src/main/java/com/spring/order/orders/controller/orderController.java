package com.spring.order.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.order.orders.DTO.OrderRequestDTO;
import com.spring.order.orders.Redis.orderCacheService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/orders")
public class orderController {

    @Autowired
    private KafkaTemplate<String, OrderRequestDTO> kafkaTemplate;

    @Autowired
    private orderCacheService orderCacheService;

    private static final String TOPIC = "order-events";

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequestDTO orderRequest) throws JsonProcessingException {
        System.out.println("Sending order: " + orderRequest.getProductName());
        kafkaTemplate.send(TOPIC, orderRequest);
    return ResponseEntity.ok("Order Placed Successfully!");
        
    }

    @GetMapping("/{orderId}")
    public Object getOrder(@PathVariable String orderId) {
        Object order = orderCacheService.getOrder("order|"+orderId);
        if (order == null) {
            return "Order expired or not found.";
        }
        return order;
    }
}