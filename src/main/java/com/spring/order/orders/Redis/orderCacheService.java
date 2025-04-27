package com.spring.order.orders.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class orderCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long ORDER_TTL_HOURS = 2; // 2 hours

    public void saveOrder(String orderId, Object orderData) {
        redisTemplate.opsForValue().set(orderId, orderData, ORDER_TTL_HOURS, TimeUnit.HOURS);
    }

    public Object getOrder(String orderId) {
        return redisTemplate.opsForValue().get(orderId);
    }
}
