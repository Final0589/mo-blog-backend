package com.mo.service;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * 上传到云端/本地
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 上传到云端/本地（带文件夹名称）
     * @param file 文件
     * @param folderName 文件夹名称
     * @return
     */
    String upload(MultipartFile file, String folderName);

}