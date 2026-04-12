package com.mo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.entity.User;

public interface UserService {

    /**
     * 通过用户名称查找账号
     * @param eq
     * @return
     */
    User getById(LambdaQueryWrapper<User> eq);

}
