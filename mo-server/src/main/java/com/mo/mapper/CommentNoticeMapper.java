package com.mo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mo.entity.Comment;
import com.mo.entity.CommentNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentNoticeMapper extends BaseMapper<CommentNotice> {
    @Select("SELECT c.*, u.avatar AS avatar, u.nickname AS userName " +
            "FROM comment c " +
            "INNER JOIN comment_notice n ON c.id = n.comment_id " +
            "LEFT JOIN user u ON c.user_id = u.id " + // 这里把 user 替换成你的用户表名
            "ORDER BY c.create_time DESC")
    List<CommentNotice> getUnreadComment();

    @Delete("DELETE FROM comment_notice WHERE comment_id = #{commentId}")
    void deleteByCommentId(Integer commentId);
}