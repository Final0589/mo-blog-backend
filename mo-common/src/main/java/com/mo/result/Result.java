package com.mo.result;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.data = data;
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(String msg) {
        Result result = new Result<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}