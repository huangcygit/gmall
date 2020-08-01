package com.hy.gmall.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.user.UmsMember;
import com.hy.gmall.service.UserService;
import com.hy.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UmsMember> getAllUser() {
//        userMapper.selectAllUser();
        List<UmsMember> result = userMapper.selectAll();
        return result;
    }
}
