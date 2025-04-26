package com.spring.order.orders.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.spring.order.orders.Redis.TokenService;
import com.spring.order.orders.Utils.JWTGeneration;
import com.spring.order.orders.Utils.LoginResponse;
import com.spring.order.orders.models.LoggedUser;
import com.spring.order.orders.models.User;
import com.spring.order.orders.respo.AuthRepo;

@Service
public class AuthService {

    private static final Logger logger = LogManager.getLogger(AuthService.class);
    private final AuthRepo authRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public AuthService(AuthRepo userRepository,
            PasswordEncoder passwordEncoder) {
        this.authRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isRegistered(String username) {
        logger.info("IsRegistered method :: started");
        Optional<User> user = authRepository.findByUsername(username);

        if (user.isPresent()) {
            logger.info("User is present");
            return true;
        }
        logger.warn("User is not present.");
        return false;

    }

    public boolean registeringUser(User user) {
        logger.info("Starting register process for user: {}", user.getUsername());
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            authRepository.save(user);
            logger.info("User saved to DB", user.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Error occur while registering the user", e);
            e.printStackTrace();
            return false;
        }
    }

    public LoginResponse isloggedIn(LoggedUser user) {
        logger.info("Starting login process for user: {}", user.getUsername());
        Optional<User> loggedinUser = authRepository.findByUsername(user.getUsername());
        if (loggedinUser.isPresent()) {
            User actualUser = loggedinUser.get();
            if (passwordEncoder.matches(user.getPassword(), actualUser.getPassword())) {
                logger.info("Login successful for user: {}", user.getUsername());
                String token = JWTGeneration.generateToken(actualUser.getUsername(), actualUser.getRole());
                String oneDayToken = JWTGeneration.generate24HoyrsToken(actualUser.getUsername(), actualUser.getRole());
                // Store tokens in Redis
                
                tokenService.saveToken(token,actualUser.getUsername());
                tokenService.saveToken24(oneDayToken, actualUser.getUsername());

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
