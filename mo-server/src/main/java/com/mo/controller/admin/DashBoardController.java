package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mo.result.Result;
import com.mo.service.DashBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@Slf4j
@SaCheckRole("admin")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    /**
     * 获取总文章阅读量
     * @return
     */
    @GetMapping("/totalview")
    public Result getTotalView() {
        Long total = dashBoardService.total();
        return Result.success(total);
    }

    /**
     * 获取今天阅读量
     * @return
     */
    // TODO 获取今天阅读量
    @GetMapping("/today")
    public Result getTodayView() {
        dashBoardService.todayView();
        return Result.success();
    }

    // TODO 获取文章新评论（ws）

}
