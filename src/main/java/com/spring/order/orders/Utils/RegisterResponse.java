package com.spring.order.orders.Utils;



public class RegisterResponse {
    private String username;
    private boolean status;
    private String message;

    // Constructors
    public RegisterResponse() {}

    public RegisterResponse(String username, boolean status, String message) {
        this.username = username;
        this.status = status;
        this.message = message;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
 