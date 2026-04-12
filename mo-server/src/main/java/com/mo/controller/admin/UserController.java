package com.mo.controller.admin;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.dto.LoginDTO;
import com.mo.entity.User;
import com.mo.result.Result;
import com.mo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result doLogin(LoginDTO loginDTO) {
        // 查询用户
        User user = userService.getById(new LambdaQueryWrapper<User>().eq(User::getName, loginDTO.getUsername()));

        // 密码比对
        if (user == null || !BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 用户登录
        StpUtil.login(user.getId());

        // 返回词元（笑）
        Map<String, Object> data = new HashMap<>();
        data.put("token", StpUtil.getTokenValue());
        data.put("permission", user.getPermission());
        data.put("nickname", user.getNickname());

        return Result.success(data);
    }



}
