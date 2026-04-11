package com.mo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mo.dto.CategoryDTO;
import com.mo.dto.CategoryPageQueryDTO;
import com.mo.entity.Category;
import com.mo.result.PageResult;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 创建分类
     * @param categoryDTO
     */
    void createCategory(CategoryDTO categoryDTO);

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 批量删除分类
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

}
