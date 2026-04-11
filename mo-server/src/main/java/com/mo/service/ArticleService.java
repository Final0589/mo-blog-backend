package com.mo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mo.dto.ArticlePageQueryDTO;
import com.mo.result.PageResult;
import com.mo.dto.ArticleDTO;
import com.mo.entity.Article;

import java.util.List;

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


    /**
     * 修改文章
     * @param articleDTO
     * @return
     */
    void updateArticle(ArticleDTO articleDTO);

    /**
     * 批量删除文章
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 设置文章状态（1为公开 2为私人）
     * @param articleId
     * @param status
     * @return
     */
    void setStatus(Integer articleId, Integer status);
}
