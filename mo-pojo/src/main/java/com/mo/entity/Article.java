package com.mo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String summary;
    private Integer categoryId;
    private Integer status;
    private Integer viewCount;
    private String updateTime;
}
