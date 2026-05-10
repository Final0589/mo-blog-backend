package com.mo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_tag")
public class ArticleTag {
    private Integer tagId;
    private Integer articleId;
}