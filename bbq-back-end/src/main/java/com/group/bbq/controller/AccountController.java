package com.group.bbq.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group.bbq.common.dto.LoginDto;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.User;
import com.group.bbq.entity.UserLoginLog;
import com.group.bbq.service.UserLoginLogService;
import com.group.bbq.service.UserService;
import com.group.bbq.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class AccountController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    private final UserLoginLogService userLoginLogService;

    @Autowired
    public AccountController(UserService userService, JwtUtils jwtUtils, UserLoginLogService userLoginLogService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.userLoginLogService = userLoginLogService;
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("email", loginDto.getEmail()));
        Assert.notNull(user, "User does not exist");

        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("Incorrect password");
        }

        if ("1".equals(user.getState())) {
            return Result.fail("The user is disabled");
        }

        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(user.getId());
        userLoginLog.setUserName(user.getUsername());
        userLoginLog.setUserRole(user.getRole());
        userLoginLog.setLoginTime(LocalDateTime.now());
        userLoginLogService.save(userLoginLog);

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("role", user.getRole())
                        .put("state", user.getState())
                        .put("createTime", user.getCreateTime())
                        .put("sex", user.getSex())
                        .put("updateTime", user.getUpdateTime())
                        .put("signature", user.getSignature())
                .map()
        );
    }

    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
