package com.hy.gmall.manage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisLock {

    @Autowired
    @Qualifier("setNxCommand")
    private RedisScript<String> setNxScript;

    @Autowired
    @Qualifier("setDelCommand")
    private RedisScript<Long> setDelScript;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final  String EXPIRE_SECONDS = "300000";

    public boolean tryLock(String key){
        List<String> keys = new ArrayList<>();
        keys.add(key);
        String value = Thread.currentThread().getName();
        String result = (String) redisTemplate.execute(setNxScript, new StringRedisSerializer(),
                new StringRedisSerializer(),keys,new Object[]{value,EXPIRE_SECONDS});

        if ("OK".equals(result)){
            return true;
        }
        return false;
    }

    public void releaseLock(String key){
        List<String> keys = new ArrayList<>();
        keys.add(key);
        String value = Thread.currentThread().getName();
        redisTemplate.execute(setDelScript, new StringRedisSerializer(),new StringRedisSerializer(), keys, value);
    }
}
