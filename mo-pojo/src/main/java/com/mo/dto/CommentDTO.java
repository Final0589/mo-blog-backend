package com.mo.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer articleId;
    private Integer userId;
    private Integer parentId;
    private String content;
    private String createTime;
}
