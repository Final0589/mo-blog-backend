package com.mo.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo.dto.CommentDTO;
import com.mo.dto.CommentPageQueryDTO;
import com.mo.entity.Comment;
import com.mo.entity.CommentNotice;
import com.mo.mapper.CommentMapper;
import com.mo.mapper.CommentNoticeMapper;
import com.mo.result.PageResult;
import com.mo.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentNoticeMapper commentNoticeMapper;

    /**
     * 发布评论
     * @param commentDTO
     */
    public void publishComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId((Integer) StpUtil.getLoginId());

        if (comment.getCreateTime() == null) {
            comment.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        
        this.save(comment);

        // 存入管理员未读
        commentNoticeMapper.insert(new CommentNotice(comment.getId()));
    }

    /**
     * 分页查询评论
     * @param dto
     * @return
     */
    public PageResult pageQuery(CommentPageQueryDTO dto) {
        Page<Comment> page = new Page<>(dto.getPage(), dto.getPageSize());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        
        // 可根据文章 ID 或用户 ID 筛选
        queryWrapper.eq(dto.getArticleId() != null, Comment::getArticleId, dto.getArticleId());
        queryWrapper.eq(dto.getUserId() != null, Comment::getUserId, dto.getUserId());
        
        queryWrapper.orderByDesc(Comment::getCreateTime);
        
        commentMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 删除评论
     * @param id
     */
    @Transactional
    public void delete(Integer id) {
        commentMapper.deleteById(id);
        
        // 如果该评论有父评论，删除所有子评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        commentMapper.delete(queryWrapper);
    }

}
