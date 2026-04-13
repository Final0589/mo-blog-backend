package com.mo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment_notice")
public class CommentNotice {
    private Integer id;
    private Integer commentId;
    public CommentNotice(Integer id) {
        this.commentId = id;
    }
}
