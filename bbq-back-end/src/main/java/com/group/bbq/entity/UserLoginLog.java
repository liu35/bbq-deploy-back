package com.group.bbq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * User Login Log
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bbq_user_login_log")
public class UserLoginLog {
    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user id
     */
    private Long userId;

    /**
     * username
     */
    private String userName;


    /**
     * user role
     */
    private String userRole;

    /**
     * login time
     */
    private LocalDateTime loginTime;




}
