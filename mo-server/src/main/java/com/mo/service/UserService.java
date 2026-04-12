package com.mo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.dto.UserDTO;
import com.mo.dto.UserPageQueryDTO;
import com.mo.entity.User;
import com.mo.result.PageResult;

import java.util.List;

public interface UserService {

    /**
     * 通过用户名称查找账号
     * @param eq
     * @return
     */
    User getById(LambdaQueryWrapper<User> eq);

    /**
     * 创建用户
     * @param userDTO
     */
    void createUser(UserDTO userDTO);

    /**
     * 修改用户
     * @param userDTO
     */
    void updateUser(UserDTO userDTO);

    /**
     * 批量删除账号
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 分页查询用户
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);
}
