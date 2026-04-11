package com.mo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo.dto.CategoryDTO;
import com.mo.dto.CategoryPageQueryDTO;
import com.mo.entity.Article;
import com.mo.entity.Category;
import com.mo.mapper.ArticleMapper;
import com.mo.mapper.CategoryMapper;
import com.mo.result.PageResult;
import com.mo.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        this.save(category);
    }

    public PageResult pageQuery(CategoryPageQueryDTO dto) {
        Page<Category> page = new Page<>(dto.getPage(), dto.getPageSize());
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(dto.getName() != null, Category::getName, dto.getName());
        queryWrapper.orderByDesc(Category::getId);
        categoryMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.updateById(category);
    }

    @Transactional
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 1. 删除分类
        categoryMapper.deleteByIds(ids);

        // 2. 将关联文章的 categoryId 置为 null（或改为默认分类 ID）
        for (Integer id : ids) {
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getCategoryId, id);
            List<Article> articles = articleMapper.selectList(queryWrapper);
            for (Article article : articles) {
                article.setCategoryId(null);
                articleMapper.updateById(article);
            }
        }
    }

}
