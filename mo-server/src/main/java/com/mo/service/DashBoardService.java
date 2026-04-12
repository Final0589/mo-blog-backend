package com.mo.service;

public interface DashBoardService {

    /**
     * 获取总文章阅读量
     * @return
     */
    Long total();

    /**
     * 获取今天阅读量
     * @return
     */
    Long todayView();


}
