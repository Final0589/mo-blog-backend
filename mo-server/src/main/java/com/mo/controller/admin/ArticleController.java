package com.mo.controller.admin;

import com.mo.dto.ArticleDTO;
import com.mo.dto.ArticlePageQueryDTO;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        log.info("保存文章中：{}", articleDTO.getTitle());
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

    /**
     * 修改文章
     * @param articleDTO
     * @return
     */
    @PutMapping("/update")
    public Result update(ArticleDTO articleDTO) {
        articleService.updateArticle(articleDTO);
        return Result.success();
    }

    /**
     * 批量删除文章
     * @param ids
     */
    @DeleteMapping("delete")
    public Result delete(List<Integer> ids) {
        articleService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 设置文章状态（1为公开 2为私人）
     * @param articleId
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    public Result setStatus(Integer articleId,@PathVariable Integer status) {
        articleService.setStatus(articleId, status);
        return Result.success();
    }

}
