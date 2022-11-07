package com.group.bbq.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.bbq.common.dto.UpdateUserPasswordDto;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.Posts;
import com.group.bbq.entity.User;
import com.group.bbq.mapper.UserMapper;
import com.group.bbq.service.UserService;
import com.group.bbq.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * user info table serviceImpl
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result register(User user) {
        User oldUser = userMapper.selectOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        Assert.isNull(oldUser, "The email has been registered");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRole("USER");
        user.setState("ENABLED");
        user.setPassword(SecureUtil.md5(user.getPassword()));
        Assert.isTrue(userMapper.insert(user) > 0, "Registration failed");
        return Result.succ(user);
    }

    @Override
    public Result edit(User user) {
        User oldUser = userMapper.selectOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (ObjectUtil.isNotEmpty(oldUser) && !oldUser.getId().equals(user.getId())) {
            return Result.fail("The email has been registered");
        }
        if (ObjectUtil.isEmpty(userMapper.selectById(user.getId()))) {
            return Result.fail("user is not exist");
        }
        if (oldUser.getId().equals(user.getId()) && ObjectUtil.isNotEmpty(oldUser)) {
            if (!oldUser.getPassword().equals(SecureUtil.md5(user.getPassword()))) {
                return Result.fail("Incorrect password");
            }
        }


        user.setUpdateTime(LocalDateTime.now());
        user.setPassword(SecureUtil.md5(user.getPassword()));
        Assert.isTrue(userMapper.updateById(user) > 0, "edit failed");
        return Result.succ(user);
    }

    @Override
    public Result updatePassword(UpdateUserPasswordDto updateUserPasswordDto) {
        User user = userMapper.selectById(updateUserPasswordDto.getId());
        if (ObjectUtil.isEmpty(user)) {
            return Result.fail("user is not exist");
        }
        user.setPassword(SecureUtil.md5(updateUserPasswordDto.getPassword()));
        Assert.isTrue(userMapper.updateById(user) > 0, "edit password failed");
        return Result.succ(user.getEmail());
    }

    @Override
    public Result setRole(Long userId, String role) {

            User user = userMapper.selectById(userId);
            if (ObjectUtil.isEmpty(user)) {
                return Result.fail("user is not exist");
            }
            user.setRole(role);
            Assert.isTrue(userMapper.updateById(user) > 0, "set role failed");
            return Result.succ(null);

    }

    @Override
    public Result setUserDisable(Long userId) {

            User user = userMapper.selectById(userId);
            if (ObjectUtil.isEmpty(user)) {
                return Result.fail("user is not exist");
            }
            user.setState("DISABLED");
            Assert.isTrue(userMapper.updateById(user) > 0, "set user disable failed");
            return Result.succ(null);

    }

    @Override
    public Result setUserEnable(Long userId) {

            User user = userMapper.selectById(userId);
            if (ObjectUtil.isEmpty(user)) {
                return Result.fail("user is not exist");
            }
            user.setState("ENABLED");
            Assert.isTrue(userMapper.updateById(user) > 0, "set user enable failed");
            return Result.succ(null);

    }

    @Override
    public Result getAllUserInfo(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);

        QueryWrapper<User> postsQueryWrapper = new QueryWrapper<>();

        IPage<User> userIPage = userMapper.selectPage(page, postsQueryWrapper);

        return Result.succ(userIPage);
    }
}
