package com.mo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mo.dto.UserPageQueryDTO;
import com.mo.entity.User;
import com.mo.mapper.UserMapper;
import com.mo.result.PageResult;
import com.mo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${blog.github-oauth.client-id}")
    private String clientId;

    @Value("${blog.github-oauth.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private final String GITHUB_API_URL = "https://api.github.com/user";

    /**
     * 批量删除账号
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
    }

    /**
     * 分页查询用户
     * @param userPageQueryDTO
     * @return
     */
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        Page<User> page = new Page<>(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(userPageQueryDTO.getName() != null, User::getNickname, userPageQueryDTO.getName());
        queryWrapper.like(userPageQueryDTO.getNickname() != null, User::getNickname, userPageQueryDTO.getNickname());
        queryWrapper.eq(userPageQueryDTO.getStatus() != null, User::getStatus, userPageQueryDTO.getStatus());
        
        userMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * Github Oauth登录
     * @param code
     * @return
     */
    public User oauth(String code) {
        // 获取access_token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(GITHUB_TOKEN_URL, requestEntity, Map.class);
        Map<String, Object> resBody = response.getBody();

        if (resBody == null || resBody.get("access_token") == null) {
            throw new RuntimeException("OAuth登录错误: " + (resBody != null ? resBody.get("error") : "获取token失败"));
        }
        String accessToken = resBody.get("access_token").toString();

        // 获取Github用户信息
        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(getHeaders);

        ResponseEntity<Map> returnUserInfo = restTemplate.exchange(GITHUB_API_URL, HttpMethod.GET, entity, Map.class);
        Map<String, Object> githubUserInfo = returnUserInfo.getBody();

        if (githubUserInfo == null || githubUserInfo.get("id") == null) {
            throw new RuntimeException("获取Github用户信息失败");
        }

        String githubId = String.valueOf(githubUserInfo.get("id"));

        // 检查用户是否创建，如果没有就创建
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getGithubId, githubId));
        if (user == null) {
            user = new User();
            user.setGithubId(githubId);
            user.setNickname((String) githubUserInfo.get("login"));
            user.setAvatar((String) githubUserInfo.get("avatar_url"));
            user.setPermission(0); // 普通用户
            user.setStatus(0);     // 状态正常
            userMapper.insert(user);
        }

        return user;
    }

    /**
     * 设置用户状态
     * @param id
     * @param status
     */
    @Override
    public void setStatus(Integer id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
    }

}
