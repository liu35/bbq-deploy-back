package com.group.bbq.common.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class AddCommentDto {
    private Long postId;
    private String username;
    private String avatar;
    private Long userId;
    private String content;
}
