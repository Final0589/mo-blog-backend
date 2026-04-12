package com.mo.configuration;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    // 注册SaToken拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 登录校验 —— 拦截/admin路由
            SaRouter.match("/admin/**", r -> StpUtil.checkLogin());}))
                .addPathPatterns("/**").excludePathPatterns("/user/login").excludePathPatterns("/admin/login"); // 开放登录接口
    }

}
