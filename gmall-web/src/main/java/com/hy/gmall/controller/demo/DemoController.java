package com.hy.gmall.controller.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.user.UmsMember;
import com.hy.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(("/demo"))
public class DemoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/demo")
    public String test(String message){
        redisTemplate.convertAndSend("redis-message-test", "first message for redis:" + message);
        return "test";
    }

}
