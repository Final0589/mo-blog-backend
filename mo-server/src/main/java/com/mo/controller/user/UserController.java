package com.mo.controller.user;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.dto.LoginDTO;
import com.mo.dto.UserDTO;
import com.mo.entity.User;
import com.mo.result.Result;
import com.mo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userUserController")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
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

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return Result.success();
    }

    /**
     * 修改个人信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody UserDTO userDTO) {
        // 设置当前登录用户ID，确保只修改自己的账号
        userDTO.setId(StpUtil.getLoginIdAsInt());
        userService.updateUser(userDTO);
        return Result.success();
    }

}
