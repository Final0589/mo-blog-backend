package com.mo.controller.admin;

import com.mo.dto.ArticleDTO;
import com.mo.dto.ArticlePageQueryDTO;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 上传文章
     * @param articleDTO
     * @return
     */
    @PostMapping("/publish")
    public Result uploadArticle(ArticleDTO articleDTO) {
        log.info("开始上传：{}", articleDTO.getTitle());
        articleService.publishArticle(articleDTO);
        return Result.success();
    }

    /**
     * 分页查询文章
     * @return
     */
    @GetMapping("/page")
    public Result page(ArticlePageQueryDTO articlePageQueryDTO) {
        PageResult pageResult = articleService.pageQuery(articlePageQueryDTO);
        return Result.success(pageResult);
    }

}
