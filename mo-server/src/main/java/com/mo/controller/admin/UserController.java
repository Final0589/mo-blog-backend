package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mo.dto.UserPageQueryDTO;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin/user")
@Slf4j
@SaCheckRole("admin")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 批量删除账号
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam List<Integer> ids) {
        userService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页查询用户
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(UserPageQueryDTO userPageQueryDTO) {
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用账号
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/status/{status}")
    public Result setStatus(Integer id, @PathVariable Integer status) {
        userService.setStatus(id, status);
        return Result.success();
    }

}
