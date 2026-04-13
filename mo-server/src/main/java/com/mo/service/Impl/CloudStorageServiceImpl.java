package com.mo.service.Impl;

import com.mo.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service("cloudStorageServiceImpl")
public class CloudStorageServiceImpl implements StorageService {

    @Value("${blog.storage.cloud.api-token}")
    private String apiToken;

    @Value("${blog.storage.cloud.upload-url}")
    private String uploadUrl;

    @Value("${blog.storage.cloud.file-body-name}")
    private String fileBodyName;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 上传到图像/本地
     * @return
     */
    public String upload(MultipartFile file) {
        // 构造头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", apiToken);

        // 构造请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(fileBodyName, file.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 上传
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(uploadUrl, requestEntity, Map.class);
            Map<String, Object> resBody = response.getBody();
            if (resBody != null && (boolean) resBody.get("success")) {
                Map<String, Object> data = (Map<String, Object>) resBody.get("data");
                return (String) data.get("url");
            } else {
                throw new RuntimeException("上传服务器返回错误: " + resBody.get("message"));
            }
        } catch (Exception e) {
            throw new RuntimeException("云端上传异常", e);
        }
    }

}
