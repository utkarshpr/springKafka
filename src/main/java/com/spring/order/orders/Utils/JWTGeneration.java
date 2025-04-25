package com.spring.order.orders.Utils;

import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
public class JWTGeneration {

    // Secret key (should be at least 256 bits for HS256)
    private static final Key SECRET_KEY = Constant.SECRET_KEY;

    // Token validity (e.g., 1 hour)
    private static final long EXPIRATION_TIME = 3600000;
    public static String generateToken(String username,String role) {
       return Jwts.builder()
                .setSubject(username)
                .setSubject(role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Optional: validate token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Optional: extract username
    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
   
    
}
