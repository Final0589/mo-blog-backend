package com.mo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo.dto.TagDTO;
import com.mo.dto.TagPageQueryDTO;
import com.mo.entity.ArticleTag;
import com.mo.entity.Tag;
import com.mo.mapper.ArticleTagMapper;
import com.mo.mapper.TagMapper;
import com.mo.result.PageResult;
import com.mo.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * 创建标签
     * @param tagDTO
     */
    public void createTag(TagDTO tagDTO) {
        // 复制属性
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);
        this.save(tag);
    }

    /**
     * 分页查询标签
     * @param dto
     * @return
     */
    public PageResult pageQuery(TagPageQueryDTO dto) {
        // 创建 MyBatis-Plus 的分页对象
        // 参数：当前页，每页显示条数
        Page<Tag> page = new Page<>(dto.getPage(), dto.getPageSize());

        // 构建查询条件 (Wrapper)
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        // 模糊查询
        queryWrapper.like(dto.getName() != null, Tag::getName, dto.getName());
        // 排序
        queryWrapper.orderByDesc(Tag::getId);

        // 执行分页查询
        // 核心点：执行完后，查询到的列表和总记录数都会自动塞进 page 对象里
        tagMapper.selectPage(page, queryWrapper);

        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 修改标签
     * @param tagDTO
     * @return
     */
    public void updateTag(TagDTO tagDTO) {
        // 复制属性
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);

        tagMapper.updateById(tag);
    }

    /**
     * 批量删除标签及其关联关系
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 删除标签表中的数据
        tagMapper.deleteByIds(ids);

        // 删除文章与标签关联表中的对应数据
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ArticleTag::getTag_id, ids);
        articleTagMapper.delete(queryWrapper);
    }

}
