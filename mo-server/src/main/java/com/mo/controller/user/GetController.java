package com.mo.controller.user;

import com.mo.dto.ArticlePageQueryDTO;
import com.mo.dto.CategoryPageQueryDTO;
import com.mo.dto.TagPageQueryDTO;
import com.mo.entity.Article;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.ArticleService;
import com.mo.service.CategoryService;
import com.mo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/get")
@Slf4j
public class GetController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    /**
     * 获取文章分类列表（分页）
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/category")
    public Result listCategories(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取文章标签列表（分页）
     * @param tagPageQueryDTO
     * @return
     */
    @GetMapping("/tag")
    public Result listTags(TagPageQueryDTO tagPageQueryDTO) {
        PageResult pageResult = tagService.pageQuery(tagPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取文章内容
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public Result getArticleById(@PathVariable Integer articleId) {
        Article article = articleService.getById(articleId);
        return Result.success(article);
    }

    /**
     * 获取文章列表（分页）
     * @param articlePageQueryDTO
     * @return
     */
    @GetMapping("/article/list")
    public Result listArticles(ArticlePageQueryDTO articlePageQueryDTO) {
        PageResult pageResult = articleService.pageQuery(articlePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 文章阅读量统计
     * @param articleId
     * @return
     */
    @PostMapping("/article/click/{articleId}")
    public Result viewCount(@PathVariable Integer articleId) {
        articleService.click(articleId);
        return Result.success();
    }

}
