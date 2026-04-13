package com.mo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mo.entity.Comment;
import com.mo.entity.CommentNotice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentNoticeMapper extends BaseMapper<CommentNotice> {
    @Select("SELECT c.* FROM comment c " +
            "INNER JOIN comment_notice n ON c.id = n.comment_id " +
            "ORDER BY c.create_time DESC")
    List<CommentNotice> getUnreadComment();
}
