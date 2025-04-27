package com.spring.order.orders.respo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.order.orders.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}