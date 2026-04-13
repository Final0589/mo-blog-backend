package com.mo.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo.dto.CommentDTO;
import com.mo.dto.CommentPageQueryDTO;
import com.mo.entity.Comment;
import com.mo.entity.CommentNotice;
import com.mo.entity.User;
import com.mo.mapper.CommentMapper;
import com.mo.mapper.CommentNoticeMapper;
import com.mo.mapper.UserMapper;
import com.mo.result.PageResult;
import com.mo.service.CommentService;
import com.mo.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

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

    /**
     * 获取评论树，包含父子评论
     * @param articleId
     * @param current
     * @param size
     * @return
     */
    public Page<CommentVO> getCommentTree(Integer articleId, int current, int size) {
        // 查询父评论
        Page<Comment> page = new Page<>(current, size);
        LambdaQueryWrapper<Comment> rootWrapper = new LambdaQueryWrapper<>();
        rootWrapper.eq(Comment::getArticleId, articleId)
                .and(w -> w.isNull(Comment::getParentId).or().eq(Comment::getParentId, 0))
                .orderByDesc(Comment::getCreateTime);
        commentMapper.selectPage(page, rootWrapper);

        List<Comment> roots = page.getRecords();
        if (roots.isEmpty()) return new Page<>(current, size, 0);

        // 找到本页所有主评论的ID集合
        List<Integer> rootIds = roots.stream().map(Comment::getId).collect(Collectors.toList());

        // 查询父评论下的所有子评论
        LambdaQueryWrapper<Comment> childWrapper = new LambdaQueryWrapper<>();
        childWrapper.eq(Comment::getArticleId, articleId)
                .in(Comment::getParentId, rootIds)
                .orderByAsc(Comment::getCreateTime);
        List<Comment> allChildren = commentMapper.selectList(childWrapper);

        // 转换并分组
        List<CommentVO> rootVOs = convertToVOList(roots);
        List<CommentVO> childVOs = convertToVOList(allChildren);

        // 将子评论按parentId分组：Map<ParentId, List<ChildVO>>
        Map<Integer, List<CommentVO>> childrenGroup = childVOs.stream()
                .collect(Collectors.groupingBy(CommentVO::getParentId));

        // 子评论列表插入对应的根评论
        for (CommentVO root : rootVOs) {
            List<CommentVO> children = childrenGroup.get(root.getId());
            root.setChildren(children != null ? children : new ArrayList<>());
        }

        Page<CommentVO> resultPage = new Page<>(current, size, page.getTotal());
        resultPage.setRecords(rootVOs);
        return resultPage;
    }

    /**
     * 将 Comment 实体列表转换为 CommentVO 列表
     * @param comments
     * @return
     */
    private List<CommentVO> convertToVOList(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return new ArrayList<>();
        }

        // 收集所有相关的用户 ID (评论发布者)
        Set<Integer> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toSet());

        // 收集所有父评论 ID，用于获取 targetNickname (被回复人昵称)
        Set<Integer> parentIds = comments.stream()
                .map(Comment::getParentId)
                .filter(id -> id != null && id != 0)
                .collect(Collectors.toSet());

        Map<Integer, Comment> parentCommentMap = new HashMap<>();
        if (!parentIds.isEmpty()) {
            List<Comment> parents = commentMapper.selectBatchIds(parentIds);
            parentCommentMap = parents.stream().collect(Collectors.toMap(Comment::getId, c -> c));
            // 同时把父评论的发布者 ID 也加入用户查询列表
            userIds.addAll(parents.stream().map(Comment::getUserId).collect(Collectors.toSet()));
        }

        // 批量查询用户资料
        Map<Integer, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 执行转换
        Map<Integer, Comment> finalParentCommentMap = parentCommentMap;
        Map<Integer, User> finalUserMap = userMap;

        return comments.stream().map(comment -> {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo);

            // 设置发布者昵称和头像
            User author = finalUserMap.get(comment.getUserId());
            if (author != null) {
                vo.setNickname(author.getNickname());
                vo.setAvatar(author.getAvatar());
            }

            // 时间字符串转LocalDateTime
            if (comment.getCreateTime() != null) {
                vo.setCreateTime(LocalDateTime.parse(comment.getCreateTime(), formatter));
            }

            // 设置回复用户昵称
            if (comment.getParentId() != null && comment.getParentId() != 0) {
                Comment parent = finalParentCommentMap.get(comment.getParentId());
                if (parent != null) {
                    User targetUser = finalUserMap.get(parent.getUserId());
                    if (targetUser != null) {
                        vo.setTargetNickname(targetUser.getNickname());
                    }
                }
            }

            return vo;
        }).collect(Collectors.toList());
    }

}
