package com.spring.order.orders.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.order.orders.Utils.LoginResponse;
import com.spring.order.orders.Utils.RegisterResponse;
import com.spring.order.orders.models.LoggedUser;
import com.spring.order.orders.models.User;
import com.spring.order.orders.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

     private static final Logger logger = LogManager.getLogger(AuthController.class);
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> RegisterUser(@Valid @RequestBody User user) {
        RegisterResponse rr =new RegisterResponse();
        if (authService.isRegistered(user.getUsername())){
            rr.setUsername(user.getUsername());
            rr.setStatus(false);
            rr.setMessage("User already registered .");
            return new ResponseEntity<>(rr, HttpStatus.BAD_REQUEST);
        }

        if(!authService.registeringUser(user)){
            rr.setUsername(user.getUsername());
            rr.setStatus(false);
            rr.setMessage("User not registered .");
            return new ResponseEntity<>(rr, HttpStatus.BAD_REQUEST);
            
        }
        rr.setUsername(user.getUsername());
        rr.setStatus(true);
        rr.setMessage("User registered .");
        return new ResponseEntity<>(rr, HttpStatus.OK);

    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> postMethodName(@Valid @RequestBody LoggedUser user) {
        LoginResponse response = authService.isloggedIn(user);
        logger.info("postMethodName:: LoginResponse: {}", response.getJWTToken());
        if (response.isStatus()) {
            logger.info("postMethodName Loggedin succesfully ", response.toString());
            return ResponseEntity.ok(response);
        } else {
            logger.info("postMethodName :: unable to log in ", response.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    
     
    
}
