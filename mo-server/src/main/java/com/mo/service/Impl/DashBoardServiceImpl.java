package com.mo.service.Impl;

import com.mo.mapper.DashBoardMapper;
import com.mo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private DashBoardMapper dashBoardMapper;

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
        // TODO 使用Redis缓存
        return null;
    }


}
