package com.mo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nickname;
    private String avatar;
    private Integer permission;
    private String githubId;
    private Integer status;
}
