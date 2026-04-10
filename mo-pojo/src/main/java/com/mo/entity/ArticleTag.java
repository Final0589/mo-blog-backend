package com.mo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_tag")
public class ArticleTag {
    private Integer tag_id;
    private Integer article_id;
}
