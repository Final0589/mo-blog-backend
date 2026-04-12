package com.mo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentPageQueryDTO implements Serializable {
    private int page;
    private int pageSize;
    private Integer articleId;
    private Integer userId;
}
