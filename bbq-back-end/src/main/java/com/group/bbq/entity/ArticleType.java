package com.group.bbq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * article type table
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bbq_article_type")
public class ArticleType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * name
     */
    private String name;

    /**
     * desc
     */
    private String description;

    /**
     * Number of times cited
     */
    private Long refCount;

    /**
     * action scope
     */
    private String scope;

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


}
