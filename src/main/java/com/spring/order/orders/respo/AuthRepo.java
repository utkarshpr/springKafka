package com.spring.order.orders.respo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.order.orders.models.User;

public interface AuthRepo extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
    
}
