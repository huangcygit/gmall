package com.example.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class DemoServiceImpl implements DemoService{
    public void say(){
        System.out.println("say hello");
    }
}
