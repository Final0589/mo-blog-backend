package com.mo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private Integer id;
    private String name;
    private String password;
    private String nickname;
    private String avatar;
    private String createTime;
    private Integer permission;
    private Integer email;
}
