package com.mo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.entity.User;
import com.mo.mapper.UserMapper;
import com.mo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查找账号
     * @param eq
     * @return
     */
    public User getById(LambdaQueryWrapper<User> eq) {
        return userMapper.selectById(eq);
    }

}
