package com.cruru;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void shouldConnectToRedis() {
        Boolean isAvailable = redisTemplate.getConnectionFactory()
                .getConnection()
                .ping()
                .equalsIgnoreCase("PONG");
        assertThat(isAvailable).isTrue();
    }

    @Test
    public void setAndGet() {
        // given
        String key = "testKey";
        String value = "testValue";

        // when
        redisTemplate.opsForValue().set(key, value);
        String actual = redisTemplate.opsForValue().get(key);

        // then
        assertThat(actual).isEqualTo(value);
        redisTemplate.delete("testKey");
    }

    @Test
    public void delete() {
        // given
        String key = "testDeleteKey";
        String value = "testValue";
        redisTemplate.opsForValue().set(key, value);

        // when
        redisTemplate.delete(key);
        String actual = redisTemplate.opsForValue().get(key);

        // then
        assertThat(actual).isNull();
    }
}
