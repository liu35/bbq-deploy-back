package com.group.bbq.controller;


import cn.hutool.core.util.ObjectUtil;
import com.group.bbq.common.dto.UpdateUserPasswordDto;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.User;
import com.group.bbq.service.UserLoginLogService;
import com.group.bbq.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * user info table web controller
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final UserLoginLogService userLoginLogService;

    @Autowired
    public UserController(UserService userService , UserLoginLogService userLoginLogService) {
        this.userService = userService;
        this.userLoginLogService = userLoginLogService;
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (ObjectUtil.isEmpty(user)) {
            return Result.fail("user is not exist");
        }
        return Result.succ(user);
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody User user) {
        return userService.edit(user);
    }

    @PostMapping("/editPassword")
    public Result updatePassword(@Validated @RequestBody UpdateUserPasswordDto updateUserPasswordDto) {
        return userService.updatePassword(updateUserPasswordDto);
    }

    @GetMapping("/admin/role/set")
    public Result setRole(@RequestParam Long userId, @RequestParam String role) {
        return userService.setRole(userId, role);
    }

    @GetMapping("/admin/disable")
    public Result setUserDisable(@RequestParam Long userId) {
        return userService.setUserDisable(userId);
    }

    @GetMapping("/admin/enable")
    public Result setUserEnable(@RequestParam Long userId) {
        return userService.setUserEnable(userId);
    }

    @GetMapping("/admin/loginLog")
    public Result getUserLoginLog(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(required = false, defaultValue = "15") Integer pageSize) {
        return userLoginLogService.getUserLoginLog(pageNum, pageSize);
    }

    @GetMapping("/admin/all")
    public Result getAllUser(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return userService.getAllUserInfo(pageNum, pageSize);
    }
}
