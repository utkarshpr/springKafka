package com.spring.order.orders.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.spring.order.orders.Utils.Constant;
import com.spring.order.orders.Utils.JWTGeneration;
import com.spring.order.orders.Utils.LoginResponse;
import com.spring.order.orders.models.LoggedUser;
import com.spring.order.orders.models.User;
import com.spring.order.orders.respo.AuthRepo;

import jakarta.validation.Valid;

@Service
public class AuthService {
    
    private static final Logger logger = LogManager.getLogger(AuthService.class);
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
            String encodedPassword = passwordEncoder.encode(user.getPassword()); 
            user.setPassword(encodedPassword);
            authRepository.save(user);
            return true; 
        } catch (Exception e) {
            
            e.printStackTrace(); 
            return false; 
        }
    }

    public LoginResponse isloggedIn(LoggedUser user) {
        logger.info("Starting login process for user: {}", user.getUsername());
       Optional<User> loggedinUser = authRepository.findByUsername(user.getUsername());
       if(loggedinUser.isPresent() ){
           User actualUser = loggedinUser.get();
           if (passwordEncoder.matches(user.getPassword(), actualUser.getPassword())) {
            logger.info("Login successful for user: {}", user.getUsername());
            String token = JWTGeneration.generateToken(actualUser.getUsername(), actualUser.getRole());
            return new LoginResponse(true, "Login successful", actualUser.getUsername(), token);
        } else {
            logger.warn("Incorrect password for user: {}", user.getUsername());
            return new LoginResponse(false, "Wrong password", user.getUsername(), null);
        }
    } else {
        logger.error("User not found: {}", user.getUsername());
        return new LoginResponse(false, "User not found", user.getUsername(), null);
    }
    }
}
