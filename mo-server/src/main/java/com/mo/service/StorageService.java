package com.mo.service;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * 上传到云端/本地
     * @return
     */
    String upload(MultipartFile file);

}
