package com.mo.service;

import com.mo.dto.UserPageQueryDTO;
import com.mo.entity.User;
import com.mo.result.PageResult;

import java.util.List;

public interface UserService {

    /**
     * 批量删除账号
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 分页查询用户
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * Github Oauth登录
     * @param code
     * @return
     */
    User oauth(String code);

    /**
     * 设置用户状态
     * @param id
     * @param status
     */
    void setStatus(Integer id, Integer status);
}
