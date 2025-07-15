package com.racheal.fundadmin.fundadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.UUID;

@Service
public class IdempotencyService {


    private final RedisTemplate<String, String> redisTemplate;

    public IdempotencyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(10));
    }

    public  String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

//    public UUID getResponse(String idempotencyKey) {
//
//    }
}
