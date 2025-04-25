package com.spring.order.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.order.orders.Utils.RegisterResponse;
import com.spring.order.orders.models.User;
import com.spring.order.orders.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> RegisterUser(@Valid @RequestBody User user) {

        // validtaion part 

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
    
     
    
}
