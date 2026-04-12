package com.mo.configuration;

import cn.dev33.satoken.stp.StpInterface;
import com.mo.entity.User;
import com.mo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return List.of();
    }

    /**
     * 返回账号角色
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 从数据库实时查询该用户的 permission
        User user = userMapper.selectById(Long.valueOf(loginId.toString()));
        List<String> list = new ArrayList<>();

        // SaToken赋予角色
        if (user.getPermission() == 1) {
            list.add("admin");
        } else {
            list.add("user");
        }
        return list;
    }

}

