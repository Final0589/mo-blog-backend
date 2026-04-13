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
    public CommentNotice(Integer id) {
        this.commentId = id;
    }
}
