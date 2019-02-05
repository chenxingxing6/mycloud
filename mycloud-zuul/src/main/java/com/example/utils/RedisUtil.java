package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @Author: cxx
 * @Date: 2019/2/5 19:24
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public void setObject(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setObject(Object key, Object value, long second) {
        redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
    }

    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(Object key){
        redisTemplate.delete(key);
    }
}
