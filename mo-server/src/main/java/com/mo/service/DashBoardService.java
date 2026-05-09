package com.mo.service;

import com.mo.entity.CommentNotice;

import java.util.List;

public interface DashBoardService {

    /**
     * 获取总文章阅读量
     * @return
     */
    Integer total();

    /**
     * 获取今天阅读量
     *
     * @return
     */
    Integer todayView();

    /**
     * 获取未读评论
     * @return
     */
    List<CommentNotice> unreadComment();

    /**
     * 删除新评论通知
     * @param commentId
     */
    void deleteNotice(Integer commentId);
}
