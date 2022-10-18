package com.group.bbq.common.dto;

import lombok.Data;

@Data
public class AddReplyDto {
    private Long commentId;
    private String username;
    private String avatar;
    private Long userId;
    private String content;
    private Long replyId;
    private String replyName;
}
