package com.mo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mo.entity.Article;
import com.mo.entity.ArticleTag;
import org.apache.ibatis.annotations.Select;


public interface DashBoardMapper extends BaseMapper<Article> {

    @Select("SELECT SUM(view_count) FROM article")
    Long total();

}
