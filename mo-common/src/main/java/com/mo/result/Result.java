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

    public static Result error(String msg, Integer code) {
        Result result = new Result<>();
        if (code == null) result.code = 0;
        else result.code = code;
        result.msg = msg;
        return result;
    }
}