package com.mo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mo.dto.ArticlePageQueryDTO;
import com.mo.result.PageResult;
import com.mo.dto.ArticleDTO;
import com.mo.entity.Article;

public interface ArticleService extends IService<Article> {

    /**
     * 上传文章
     * @param articleDTO
     */
    void publishArticle(ArticleDTO articleDTO);

    /**
     * 分页查询文章
     * @param articlePageQueryDTO
     * @return
     */
    PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO);
}
