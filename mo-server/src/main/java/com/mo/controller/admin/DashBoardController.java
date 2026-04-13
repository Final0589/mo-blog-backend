package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mo.entity.CommentNotice;
import com.mo.result.Result;
import com.mo.service.DashBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/today")
    public Result getTodayView() {
        Long todayView = dashBoardService.todayView();
        return Result.success(todayView);
    }

    /**
     * 获取未读评论
     * @return
     */
    @GetMapping("/unread")
    public Result getUnreadComment() {
        List<CommentNotice> commentNotices = dashBoardService.unreadComment();
        return Result.success(commentNotices);
    }

    /**
     * 标记已读评论
     * @param commentId
     * @return
     */
    @DeleteMapping("/read/{commentId}")
    public Result readComment(Integer commentId) {
        dashBoardService.deleteNotice(commentId);
        return Result.success();
    }

}
