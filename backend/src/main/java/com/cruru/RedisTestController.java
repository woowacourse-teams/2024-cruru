package com.cruru;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis-test")
public class RedisTestController {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTestController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // curl -X POST "http://localhost:8080/redis-test?key=testKey&value=testValue"
    @PostMapping
    public ResponseEntity<String> setKey(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return ResponseEntity.ok("key-value: " + key + " = " + value);
    }

    // http://localhost:8080/redis-test?key=testKey
    @GetMapping
    public ResponseEntity<String> getKey(@RequestParam String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("key " + key + " not found");
        }
        return ResponseEntity.ok("value for key " + key + " is: " + value);
    }
}
