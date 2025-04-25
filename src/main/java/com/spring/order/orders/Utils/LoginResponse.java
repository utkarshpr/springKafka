package com.spring.order.orders.Utils;

public class LoginResponse {

    private String username;
    private String message;
   
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getJWTToken() {
        return JWTToken;
    }
    public void setJWTToken(String jWTToken) {
        JWTToken = jWTToken;
    }
    private boolean status;
    private String JWTToken;
    public LoginResponse(boolean b, String string, String username2, String token) {
        this.JWTToken=token;
        this.status=b;
        this.username=username2;
        this.message=string;
    }

}
