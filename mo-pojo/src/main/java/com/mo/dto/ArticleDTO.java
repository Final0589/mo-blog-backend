package com.mo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {
    private String title;
    private String summary;
    private String content; // Markdown 源码
    private Integer categoryId;
    private List<Integer> tagIds; // 你要求的标签 ID 数组
    private Integer status; // 0-草稿, 1-发布
}
