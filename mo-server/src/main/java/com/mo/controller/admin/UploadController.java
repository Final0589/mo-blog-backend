package com.mo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mo.result.Result;
import com.mo.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/upload")
@Slf4j
@SaCheckRole("admin")
public class UploadController {

    @Autowired
    @Qualifier("localStorageServiceImpl")
    private StorageService localStorageService;

    @Autowired
    @Qualifier("cloudStorageServiceImpl")
    private StorageService cloudStorageService;

    /**
     * 上传到本地
     * @param file
     * @return
     */
    @PostMapping("/local")
    public Result<String> uploadToLocal(MultipartFile file) {
        String filepath = localStorageService.upload(file);
        return Result.success(filepath);
    }

    /**
     * 上传到云端
     * @param file
     * @return
     */
    @PostMapping("/cloud")
    public Result<String> uploadToOSS(MultipartFile file) {
        String filepath = cloudStorageService.upload(file);
        return Result.success(filepath);
    }

}
