package com.svarto.sitespringredis.config;

import com.svarto.sitespringredis.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;


@Configuration
@EnableRedisRepositories(basePackages = "com.svarto.sitespringredis.repos")
public class RedisConfig {
    @Bean
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        return connectionFactory;
    }
    @Bean
    public RedisTemplate<String, User> redisTemplate(){
        RedisTemplate<String, User> redisTemplate=new RedisTemplate<String, User>();
        redisTemplate.setConnectionFactory(getConnectionFactory());
        return redisTemplate;
    }
    @Bean
    public RedisAtomicLong idGenerator() {
        return new RedisAtomicLong("id_generator", getConnectionFactory());
    }
}
