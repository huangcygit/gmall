package com.hy.gmall.manage;

import com.hy.gmall.manage.demo.RedisDemo;
import com.hy.gmall.manage.util.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GmallManageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    @Autowired
    private RedisDemo redisDemo;

    @Autowired
    private RedisLock redisLock;

    @Test
    public void redis(){
        redisDemo.set("testK", "aaaaaaaaa");
        Object value = redisDemo.get("testK");
        System.out.println(value);
    }

    @Test
    public void lock(){
        redisLock.tryLock("lock");
        System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        redisLock.releaseLock("lock");
        System.out.println("1111111111111111111111111111");
    }
}
