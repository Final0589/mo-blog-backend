package com.mo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticlePageQueryDTO implements Serializable {
    private int page;
    private int pageSize;
    private String name;
    private List<Integer> tagIds;
    private Integer categoryId;
    private Integer articleId;
    private Integer status;
}
