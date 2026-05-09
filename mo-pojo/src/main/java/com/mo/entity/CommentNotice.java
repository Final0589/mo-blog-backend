package com.mo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment_notice")
public class CommentNotice {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer commentId;
    private String content;
    private Integer articleId;
    private String avatar;
    private String userName;
    public CommentNotice(Integer id) {
        this.commentId = id;
    }
}
