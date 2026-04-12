package com.mo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class UploadConfigure implements WebMvcConfigurer {

    @Value("${blog.storage.local.path}")
    private String uploadPath;

    /**
     * 映射上传图片
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取绝对路径
        String rootPath = System.getProperty("user.dir");

        // 确保路径以"/"结尾
        String absolutePath = rootPath + File.separator + uploadPath;
        if (!absolutePath.endsWith(File.separator)) {
            absolutePath += File.separator;
        }

        // 核心映射逻辑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + absolutePath);
    }

}
