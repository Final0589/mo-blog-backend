package com.mo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo.mapper.ArticleMapper;
import com.mo.mapper.ArticleTagMapper;
import com.mo.service.ArticleService;
import com.mo.dto.ArticleDTO;
import com.mo.dto.ArticlePageQueryDTO;
import com.mo.entity.Article;
import com.mo.entity.ArticleTag;
import com.mo.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 上传文章
     * @param articleDTO
     */
    @Override
    public void publishArticle(ArticleDTO articleDTO) {
        // DTO复制属性
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        this.save(article);

        // 保存文章与标签的关联
        List<Integer> tagIds = articleDTO.getTagIds();
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Integer tagId : tagIds) {
                ArticleTag at = new ArticleTag();
                at.setArticle_id(article.getId());
                at.setTag_id(tagId);
                articleTagMapper.insert(at);
            }
        }
    }

    /**
     * 分页查询文章
     * @param dto
     * @return
     */
    public PageResult pageQuery(ArticlePageQueryDTO dto) {
        // 创建 MyBatis-Plus 的分页对象
        // 参数：当前页，每页显示条数
        Page<Article> page = new Page<>(dto.getPage(), dto.getPageSize());

        // 构建查询条件 (Wrapper)
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 模糊查询
        queryWrapper.like(dto.getName() != null, Article::getTitle, dto.getName());
        // 排序
        queryWrapper.orderByDesc(Article::getUpdateTime);

        // 执行分页查询
        // 核心点：执行完后，查询到的列表和总记录数都会自动塞进 page 对象里
        articleMapper.selectPage(page, queryWrapper);

        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 修改文章
     * @param articleDTO
     * @return
     */
    public void updateArticle(ArticleDTO articleDTO) {
        // DTO复制属性
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);

        articleMapper.updateById(article);
    }

    /**
     * 批量删除文章
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        articleMapper.deleteByIds(ids);
    }

    /**
     * 设置文章状态（1为公开 2为私人）
     * @param articleId
     * @param status
     * @return
     */
    public void setStatus(Integer articleId, Integer status) {
        Article article = articleMapper.selectById(articleId);
        article.setStatus(status);
        articleMapper.updateById(article);
    }

}
