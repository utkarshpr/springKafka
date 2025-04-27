package com.spring.order.orders.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.spring.order.orders.DTO.OrderRequestDTO;
import com.spring.order.orders.Redis.orderCacheService;
import com.spring.order.orders.models.Order;
import com.spring.order.orders.respo.OrderRepository;

@Service
public class orderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private orderCacheService orderCacheService;

    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void consumeOrder(OrderRequestDTO orderRequestDTO) {
        try {
            System.out.println("Consumed order: " + orderRequestDTO);
            // Business Logic:
            Order order = new Order();
            order.setProductName(orderRequestDTO.getProductName());
            order.setQuantity(orderRequestDTO.getQuantity());
            order.setPrice(orderRequestDTO.getPrice());

            double totalPrice = orderRequestDTO.getPrice() * orderRequestDTO.getQuantity();
            order.setTotalPrice(totalPrice);

            if (totalPrice > 100000) {
                order.setOrderStatus("HIGH_VALUE_ORDER");
            } else if (orderRequestDTO.getQuantity() >= 10) {
                order.setOrderStatus("BULK_ORDER_DISCOUNT");
                order.setTotalPrice(totalPrice * 0.95); // 5% discount
            } else {
                order.setOrderStatus("NEW");
            }

            order.setTrackingId(UUID.randomUUID().toString());
            order.setCreatedAt(new Date());

            // Save to DB
            Order savedOrder = orderRepository.save(order);

            // Save to Redis
            orderCacheService.saveOrder("order|" + order.getId(), order);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
