package com.mo.service.Impl;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.dto.UserDTO;
import com.mo.entity.User;
import com.mo.mapper.UserMapper;
import com.mo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查找账号
     * @param eq
     * @return
     */
    @Override
    public User getById(LambdaQueryWrapper<User> eq) {
        return userMapper.selectById(eq);
    }

    /**
     * 创建用户
     * @param userDTO
     */
    public void createUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        // 密码加密
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        // 设置创建时间
        user.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        userMapper.insert(user);
    }

    /**
     * 修改用户
     * @param userDTO
     */
    public void updateUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        // 密码加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(BCrypt.hashpw(user.getPassword()));
        }

        userMapper.updateById(user);
    }

    /**
     * 批量删除账号
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
    }

}
