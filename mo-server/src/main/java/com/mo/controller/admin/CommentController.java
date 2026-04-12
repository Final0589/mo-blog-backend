package com.mo.controller.admin;

import com.mo.result.Result;
import com.mo.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/admin/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 删除单条评论
     * @param commentId
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    public Result delete(@PathVariable Integer commentId) {
        commentService.delete(commentId);
        return Result.success();
    }

}
