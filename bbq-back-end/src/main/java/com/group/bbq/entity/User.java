package com.group.bbq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * user info table
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bbq_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user email
     */
    @NotBlank(message = "the email can't be empty")
    private String email;

    /**
     * username
     */
    @NotBlank(message = "the username can't be empty")

    private String username;

    /**
     * password
     */
    @NotBlank(message = "the password can't be empty")
    private String password;

    /**
     * role
     */
    private String role;

    /**
     * state
     */
    private String state;

    /**
     * sex
     */
    @NotBlank(message = "the sex can't be empty")
    private String sex;

    /**
     * Personal image
     */
    private String avatar;

    /**
     * Personal profile
     */
    private String signature;

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
