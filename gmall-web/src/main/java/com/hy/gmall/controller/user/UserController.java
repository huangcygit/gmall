package com.hy.gmall.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.user.UmsMember;
import com.hy.gmall.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/user"))
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/getAllUser")
    public List<UmsMember> getAllUser(){
        List<UmsMember> users = userService.getAllUser();
        return users;
    }
}
