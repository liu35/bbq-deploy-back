package com.group.bbq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * posts table
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bbq_posts")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * audit state
     */
    private String auditState;

    /**
     * category
     */
    @NotBlank(message = "the category can't be empty")
    private String category;

    /**
     * author id
     */
    private Long authorId;

    /**
     * title
     */
    @NotBlank(message = "the title can't be empty")
    private String title;

    /**
     * content type
     */
    private String contentType;

    /**
     * md content
     */
    @NotBlank(message = "the content can't be empty")
    private String markdownContent;

    /**
     * html content
     */
    private String htmlContent;

    /**
     * article views
     */
    private Long views;

    /**
     * Liked/collected amount
     */
    private Long approvals;

    /**
     * Comment quantity
     */
    private Long comments;

    /**
     * Article type ID
     */
    private Long typeId;

    /**
     * Head picture of article
     */
    private String headImg;

    /**
     * official
     */
    private Integer official;

    /**
     * sticky
     */
    private Integer top;

    /**
     * sort
     */
    private Integer sort;

    /**
     * marrow
     */
    private Integer marrow;

    /**
     * Deletion (0: not deleted, 1: deleted)
     */
    private Integer isDelete;

    /**
     * record create time
     */
    private LocalDateTime createTime;

    /**
     * record update time
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String authorName;

    @TableField(exist = false)
    private String type;


}
