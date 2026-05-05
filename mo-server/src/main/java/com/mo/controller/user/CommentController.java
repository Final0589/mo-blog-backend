package com.mo.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mo.dto.CommentDTO;
import com.mo.dto.CommentPageQueryDTO;
import com.mo.entity.Comment;
import com.mo.mapper.CommentMapper;
import com.mo.mapper.UserMapper;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.CommentService;
import com.mo.service.UserService;
import com.mo.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userCommentController")
@RequestMapping("/user/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 发表评论
     * @param commentDTO
     * @return
     */
    @PostMapping("/publish")
    @SaCheckLogin
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
    @SaCheckLogin
    public Result replyComment(@RequestBody CommentDTO commentDTO) {
        commentService.publishComment(commentDTO);
        return Result.success();
    }

    /**
     * 根据文章ID查询评论
     * @param articleId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/{articleId}")
    public Result<Page<CommentVO>> getComments(@PathVariable Integer articleId,
                                               @RequestParam(defaultValue = "1") int current,
                                               @RequestParam(defaultValue = "10") int size) {
        Page<CommentVO> commentTree = commentService.getCommentTree(articleId, current, size);
        return Result.success(commentTree);
    }

    /**
     * 删除自己发布的评论
     * @param commentId
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    @SaCheckLogin
    public Result delete(@PathVariable Integer commentId) {
        // 先查询该评论是否为当前用户发送，不是则无法删除
        Comment comment = commentMapper.selectById(commentId);
        System.out.println(StpUtil.getLoginId());
        if (StpUtil.getLoginIdAsInt() == comment.getUserId() | userMapper.selectById(StpUtil.getLoginIdAsInt()).getPermission() == 1) {
            commentService.delete(commentId);
            return Result.success();
        } else {
            return Result.error("无法删除别人的评论", 403);
        }
    }

}
