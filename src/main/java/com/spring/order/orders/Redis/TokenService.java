package com.spring.order.orders.Redis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String TOKEN_KEY = "auth_token:";
    private final String TOKEN_KEY_24 = "auth_token24:";

    public void saveToken(String token, String longExpiryToken, String userId) {
        Map<String, String> fields = new HashMap<>();
        fields.put(TOKEN_KEY, token);
        fields.put(TOKEN_KEY_24, longExpiryToken);

        // Save to Redis Hash
        redisTemplate.opsForHash().putAll(userId, fields);
        // Set an expiration time for the key (e.g., 4 hours)
        redisTemplate.expire(userId, 4, TimeUnit.HOURS);
    }

    public Map<Object, Object> getToken(String userId) {
        // Fetch the entire hash stored for the user ID
        Map<Object, Object> tokenData = redisTemplate.opsForHash().entries(userId);

        // Log the fetched data for debugging
        System.out.println("Fetched Token Data: " + tokenData);

        return tokenData;
    }
}

