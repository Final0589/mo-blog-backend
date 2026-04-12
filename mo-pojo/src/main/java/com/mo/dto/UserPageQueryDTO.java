package com.mo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPageQueryDTO implements Serializable {
    private int page;
    private int pageSize;
    private String name;
    private String nickname;
    private Integer status;
}
