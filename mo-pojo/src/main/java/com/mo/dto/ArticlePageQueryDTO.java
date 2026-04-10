package com.mo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticlePageQueryDTO implements Serializable {
    private int page;
    private int pageSize;
    private String name;
    private Integer articleId;
    private Integer status;
}
