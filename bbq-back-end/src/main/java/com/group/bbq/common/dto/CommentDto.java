package com.group.bbq.common.dto;

import com.group.bbq.entity.CommentReply;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private Integer replyNum;
    private String content;
    private LocalDateTime updateTime;
    private List<CommentReply> commentReplyList;
}
