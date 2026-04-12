package com.mo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {
    private String title;
    private String summary;
    private String content; // Markdown 源码
    private Integer categoryId;
    private List<Integer> tagIds; // 标签数组
    private Long viewCount; // 阅读数，默认从0
    private Integer status; // 0-公开, 1-私人
}
