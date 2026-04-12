package com.mo.controller.admin;

import com.mo.dto.CategoryDTO;
import com.mo.dto.CategoryPageQueryDTO;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping("/create")
    public Result createCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO.getName());
        categoryService.createCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping("update")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类：{}", categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 批量删除分类
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        log.info("批量删除分类：{}", ids);
        categoryService.deleteBatch(ids);
        return Result.success();
    }

}
