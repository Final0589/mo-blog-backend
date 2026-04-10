package com.mo.utils;

import com.mo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 捕获所有基础异常
    @ExceptionHandler(Exception.class)
    public Result e(Exception e) {
        log.error("系统运行异常: ", e);
        return Result.error("系统运行异常" + e);
    }

}