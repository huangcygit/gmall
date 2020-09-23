package com.hy.gmall.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisScriptConfig {

    @Bean
    public RedisScript<String> setNxCommand(){
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-setnx.lua")));
        redisScript.setResultType(String.class);
        return redisScript;
    }

    @Bean
    public RedisScript<Long> setDelCommand(){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-del.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
