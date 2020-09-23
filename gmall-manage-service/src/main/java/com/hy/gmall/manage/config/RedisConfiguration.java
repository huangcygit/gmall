package com.hy.gmall.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * 根据配置自适应方式连接redis
 */
//@Configuration
public class RedisConfiguration {

    @Bean
    RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory();
    }
}
