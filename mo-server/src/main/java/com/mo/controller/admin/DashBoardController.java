package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@Slf4j
@SaCheckRole("admin")
public class DashBoardController {
    // TODO 仪表盘功能
    @GetMapping("/totalview")
    public Result getTotalView() {
        return Result.success();
    }

    @GetMapping("/today")
    public Result getTodayView() {
        return Result.success();
    }

    // TODO 获取文章新评论（ws）
}
