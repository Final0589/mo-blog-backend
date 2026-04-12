package com.mo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mo.dto.CommentDTO;
import com.mo.dto.CommentPageQueryDTO;
import com.mo.entity.Comment;
import com.mo.result.PageResult;

import java.util.List;

public interface CommentService extends IService<Comment> {

    /**
     * 发表评论
     * @param commentDTO
     */
    void publishComment(CommentDTO commentDTO);

    /**
     * 分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 删除单条评论（管理端）
     * @param id
     */
    void delete(Integer id);

}
