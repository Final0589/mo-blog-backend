package com.mo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentVO {
    private Integer id;
    private String content;
    private String nickname;
    private String avatar;
    private LocalDateTime createTime;
    private Integer parentId;
    private String targetNickname;
    private List<CommentVO> children = new ArrayList<>();
}
