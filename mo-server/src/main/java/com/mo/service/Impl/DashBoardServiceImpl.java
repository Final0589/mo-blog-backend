package com.mo.service.Impl;

import com.mo.entity.CommentNotice;
import com.mo.mapper.CommentNoticeMapper;
import com.mo.mapper.DashBoardMapper;
import com.mo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private DashBoardMapper dashBoardMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CommentNoticeMapper commentNoticeMapper;

    /**
     * 获取总文章阅读量
     * @return
     */
    public Long total() {
        // 查找Article的view列
        return dashBoardMapper.total();
    }

    /**
     * 获取今天阅读量
     * @return
     */
    public Long todayView() {
        return (Long) redisTemplate.opsForValue().get("blog:today_view");
    }

    /**
     * 获取未读评论
     * @return
     */
    public List<CommentNotice> unreadComment() {
        return commentNoticeMapper.getUnreadComment();
    }

    /**
     * 删除新评论通知
     * @param commentId
     */
    public void deleteNotice(Integer commentId) {
        commentNoticeMapper.deleteById(commentId);
    }

}
