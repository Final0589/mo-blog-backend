package com.mo.controller.user;

import cn.dev33.satoken.stp.StpUtil;
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
     * @param code
     * @return
     */
    @GetMapping("/login/{code}")
    public Result login(@PathVariable String code) {
        // 调用OAuth
        User user = userService.oauth(code);

        // 用户登录
        if (user == null) { return Result.error("登录失败", 500); }
        StpUtil.login(user.getId());

        if (user.getStatus() == 1) { return Result.error("账号已被封禁", 403); }

        // 返回词元（笑）
        Map<String, Object> data = new HashMap<>();
        data.put("token", StpUtil.getTokenValue());
        data.put("permission", user.getPermission());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("userId", user.getId());

        return Result.success(data);
    }

}
