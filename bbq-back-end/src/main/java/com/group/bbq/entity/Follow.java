package com.group.bbq.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * user follow table
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bbq_follow")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Concerned person
     */
    private Long followed;

    /**
     * Pay attention to people
     */
    private Long follower;

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
    private User followerInfo;
    @TableField(exist = false)
    private User followedInfo;

}
