package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.dto.LoginDTO;
import com.mo.dto.UserDTO;
import com.mo.dto.UserPageQueryDTO;
import com.mo.entity.User;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("adminUserController")
@RequestMapping("/admin/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(LoginDTO loginDTO) {
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
     * 创建账号
     * @param userDTO
     * @return
     */
    @PostMapping("/create")
    @SaCheckRole("admin")
    public Result create(UserDTO userDTO) {
        userService.createUser(userDTO);
        return Result.success();
    }

    /**
     * 修改管理员账号信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @SaCheckRole("admin")
    public Result update(UserDTO userDTO) {
        userService.updateUser(userDTO);
        return Result.success();
    }

    /**
     * 批量删除账号
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    @SaCheckRole("admin")
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除账号中：{}", ids);
        userService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页查询用户
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @SaCheckRole("admin")
    public Result<PageResult> page(UserPageQueryDTO userPageQueryDTO) {
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }

}
