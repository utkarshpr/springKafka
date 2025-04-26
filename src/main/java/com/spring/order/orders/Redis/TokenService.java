package com.spring.order.orders.Redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private  RedisTemplate<String, String> redisTemplate;

    private  final String TOKEN_KEY_PREFIX = "auth_token:";
    private  final String TOKEN_KEY_PREFIX_24 = "auth_token24:";
    public  void saveToken(String token, String userId) {
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + userId, token, 1, TimeUnit.HOURS); // Expiration in 1 hour
    }

    public  void saveToken24(String token, String userId) {
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + userId, token, 24, TimeUnit.HOURS); // Expiration in 6 hour
    }

    public  String getToken(String userId) {
        return redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + userId);
    }

    public  String getToken24(String userId) {
        return redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX_24 + userId);
    }
}
