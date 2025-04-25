package com.spring.order.orders.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.order.orders.models.User;
import com.spring.order.orders.respo.AuthRepo;

@Service
public class AuthService {
    
    private final AuthRepo authRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthRepo userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isRegistered(String username){
        Optional<User> user=authRepository.findByUsername(username);

        if (user.isPresent()){
                return true;
        }

        return false;

    }

    public boolean registeringUser(User user) {
        try {
            authRepository.save(user);
            return true; 
        } catch (Exception e) {
            
            e.printStackTrace(); 
            return false; 
        }
    }
}
