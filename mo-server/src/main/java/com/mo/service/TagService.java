package com.mo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mo.dto.TagDTO;
import com.mo.dto.TagPageQueryDTO;
import com.mo.entity.Tag;
import com.mo.result.PageResult;

import java.util.List;

public interface TagService extends IService<Tag> {

    /**
     * 创建标签
     * @param tagDTO
     */
    void createTag(TagDTO tagDTO);

    /**
     * 分页查询标签
     * @param tagPageQueryDTO
     * @return
     */
    PageResult pageQuery(TagPageQueryDTO tagPageQueryDTO);

    /**
     * 修改标签
     * @param tagDTO
     * @return
     */
    void updateTag(TagDTO tagDTO);

    /**
     * 批量删除标签
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

}
