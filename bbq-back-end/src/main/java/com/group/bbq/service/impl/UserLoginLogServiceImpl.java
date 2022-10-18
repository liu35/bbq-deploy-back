package com.group.bbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.User;
import com.group.bbq.entity.UserLoginLog;
import com.group.bbq.mapper.UserLoginLogMapper;
import com.group.bbq.service.UserLoginLogService;
import com.group.bbq.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {

    private final UserLoginLogMapper userLoginLogMapper;

    @Autowired
    public UserLoginLogServiceImpl(UserLoginLogMapper userLoginLogMapper) {
        this.userLoginLogMapper = userLoginLogMapper;
    }

    @Override
    public Result getUserLoginLog(int pageNum, int pageSize) {
        Page<UserLoginLog> page = new Page<>(pageNum, pageSize);

        QueryWrapper<UserLoginLog> postsQueryWrapper = new QueryWrapper<>();

        IPage<UserLoginLog> userIPage = userLoginLogMapper.selectPage(page, postsQueryWrapper);

        return Result.succ(userIPage);

    }
}
