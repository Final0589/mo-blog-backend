package com.mo.service.Impl;

import com.mo.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("localStorageServiceImpl")
public class LocalStorageServiceImpl implements StorageService {

    @Value("${blog.storage.local.path}")
    private String localPath;

    /**
     * 上传到本地
     * @return
     */
    public String upload(MultipartFile file) {
        try {
            // 生成绝对路径
            String rootPath = System.getProperty("user.dir");
            File uploadFolder = new File(rootPath, localPath);

            // 检查并创建目录
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            // UUID+文件名
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString().replace("-", "") + "-" + suffix;

            // 保存相对路径
            File destFile = new File(uploadFolder, newFilename);
            file.transferTo(destFile);

            return newFilename;
        } catch (IOException e) {
            throw new RuntimeException("保存失败：", e);
        }
    }

}
