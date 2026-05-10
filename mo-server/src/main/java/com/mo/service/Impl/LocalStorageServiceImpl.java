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
        return upload(file, null);
    }

    /**
     * 上传到本地（带文件夹名称）
     * @param file 文件
     * @param folderName 文件夹名称
     * @return
     */
    public String upload(MultipartFile file, String folderName) {
        try {
            // 生成绝对路径
            String rootPath = System.getProperty("user.dir");
            File uploadFolder = new File(rootPath, localPath);

            // 检查并创建目录
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            // 如果有文件夹名称，创建对应文件夹
            if (folderName != null && !folderName.isEmpty()) {
                File targetFolder = new File(uploadFolder, folderName);
                if (!targetFolder.exists()) {
                    targetFolder.mkdirs();
                }
                uploadFolder = targetFolder;
            }

            // UUID+原始文件名
            String originalFilename = file.getOriginalFilename();
            String newFilename = UUID.randomUUID().toString().replace("-", "") + "-" + originalFilename;

            // 保存相对路径
            File destFile = new File(uploadFolder, newFilename);
            file.transferTo(destFile);

            // 返回路径（包含文件夹名称）
            if (folderName != null && !folderName.isEmpty()) {
                return folderName + "/" + newFilename;
            }
            return newFilename;
        } catch (IOException e) {
            throw new RuntimeException("保存失败：", e);
        }
    }

}