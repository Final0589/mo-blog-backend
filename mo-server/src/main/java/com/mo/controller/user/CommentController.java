package com.mo.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.mo.dto.CommentDTO;
import com.mo.dto.CommentPageQueryDTO;
import com.mo.entity.Comment;
import com.mo.mapper.CommentMapper;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userCommentController")
@RequestMapping("/user/comment")
@Slf4j
@SaCheckLogin
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 发表评论
     * @param commentDTO
     * @return
     */
    @PostMapping("/publish")
    public Result publishComment(@RequestBody CommentDTO commentDTO) {
        commentService.publishComment(commentDTO);
        return Result.success();
    }

    /**
     * 回复评论（带父评论id）
     * @param commentDTO
     * @return
     */
    @PostMapping("/reply")
    public Result replyComment(@RequestBody CommentDTO commentDTO) {
        commentService.publishComment(commentDTO);
        return Result.success();
    }

    /**
     * 根据文章ID分页查询评论
     * @param articleId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/{articleId}")
    public Result page(@PathVariable Integer articleId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        CommentPageQueryDTO commentPageQueryDTO = new CommentPageQueryDTO();
        commentPageQueryDTO.setArticleId(articleId);
        commentPageQueryDTO.setPage(page);
        commentPageQueryDTO.setPageSize(pageSize);

        PageResult pageResult = commentService.pageQuery(commentPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除自己发布的评论
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    public Result delete(@PathVariable Integer commentId) {
        // 先查询该评论是否为当前用户发送，不是则无法删除
        Comment comment = commentMapper.selectById(commentId);
        if (StpUtil.getLoginId().equals(comment.getUserId())) {
            commentService.delete(commentId);
            return Result.success();
        } else {
            return Result.error("无法删除别人的评论");
        }
    }

}
