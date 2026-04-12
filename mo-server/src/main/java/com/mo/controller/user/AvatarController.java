package com.mo.controller.user;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.mo.entity.User;
import com.mo.mapper.UserMapper;
import com.mo.result.Result;
import com.mo.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/avatar")
@SaCheckRole("user")
public class AvatarController {

    @Autowired
    @Qualifier("localStorageServiceImpl")
    private StorageService localStorageService;

    @Autowired
    @Qualifier("cloudStorageServiceImpl")
    private StorageService cloudStorageService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 上传头像并保存路径到数据库
     * @param file
     * @return
     */
    @PostMapping("upload")
    public Result upload(MultipartFile file) {
        // 优先上传文件到云端，如果失败就保存到本地
        String avatarUrl = "";
        try {avatarUrl = cloudStorageService.upload(file);} catch (Exception e) {
            avatarUrl = localStorageService.upload(file);
        }
        
        // 获取ID
        Integer userId = StpUtil.getLoginIdAsInt();

        // 更新路径
        User user = new User();
        user.setId(userId);
        if (avatarUrl.isBlank()) {
            return Result.error("头像上传失败");
        }
        user.setAvatar(avatarUrl);
        userMapper.updateById(user);

        return Result.success();
    }

}
