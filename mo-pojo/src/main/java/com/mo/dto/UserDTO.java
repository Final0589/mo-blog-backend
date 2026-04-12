package com.mo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String password;
    private String nickname;
    private String avatar;
    private Integer permission;
    private String email;
}
