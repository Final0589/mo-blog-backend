package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mo.dto.TagDTO;
import com.mo.dto.TagPageQueryDTO;
import com.mo.result.PageResult;
import com.mo.result.Result;
import com.mo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
@Slf4j
@SaCheckRole("admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 创建标签
     * @param tagDTO
     * @return
     */
    @PostMapping("/create")
    public Result uploadTag(TagDTO tagDTO) {
        log.info("创建标签中：{}", tagDTO.getName());
        tagService.createTag(tagDTO);
        return Result.success();
    }

    /**
     * 分页查询标签
     * @return
     */
    @GetMapping("/page")
    public Result page(TagPageQueryDTO tagPageQueryDTO) {
        PageResult pageResult = tagService.pageQuery(tagPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改标签
     * @param tagDTO
     * @return
     */
    @PutMapping("/update")
    public Result update(TagDTO tagDTO) {
        tagService.updateTag(tagDTO);
        return Result.success();
    }
    /**
     * 批量删除标签
     * @param ids
     */
    @DeleteMapping("/delete")
    public Result delete(List<Integer> ids) {
        tagService.deleteBatch(ids);
        return Result.success();
    }

}
