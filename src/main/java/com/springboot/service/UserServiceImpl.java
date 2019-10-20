package com.springboot.service;

import com.springboot.dto.User;
import com.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public void insert(User user) {
        userMapper.addUser(user.getAccount_id(),user.getToken(),user.getName());
    }
}
