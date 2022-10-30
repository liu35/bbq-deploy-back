package com.group.bbq.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.Follow;
import com.group.bbq.entity.User;
import com.group.bbq.mapper.FollowMapper;
import com.group.bbq.mapper.UserMapper;
import com.group.bbq.service.FollowService;
import com.group.bbq.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * user follow table
 * </p>
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    private final FollowMapper followMapper;
    private final UserMapper userMapper;

    @Autowired
    public FollowServiceImpl(FollowMapper followMapper,UserMapper userMapper) {
        this.followMapper = followMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Result add(Long authorId, Long followerId) {
        if (ObjectUtil.isNotEmpty(getFollow(authorId, followerId))) {
            return Result.fail("You're already following the user");
        }

        Follow follow = new Follow();
        follow.setFollowed(authorId);
        follow.setFollower(followerId);
        follow.setCreateTime(LocalDateTime.now());

        Follow follow1 = followMapper.selectOne(new QueryWrapper<Follow>().
                eq("followed", followerId).
                eq("follower", authorId));
        if (follow1 == null){
            follow.setIsDelete(0);
        }else {
            follow.setIsDelete(1);
            follow1.setIsDelete(1);
            followMapper.updateById(follow1);
        }
        followMapper.insert(follow);
        return Result.succ(null);
    }

    @Override
    public Result delete(Long authorId, Long followerId) {
        Follow follow = getFollow(authorId, followerId);

        if (ObjectUtil.isEmpty(follow)) {
            return Result.fail("You're not following the user anymore");
        }
        followMapper.deleteById(follow.getId());
        Follow follow1 = followMapper.selectOne(new QueryWrapper<Follow>().
                eq("followed", followerId).
                eq("follower", authorId));
        if (follow1 != null){
            follow1.setIsDelete(0);
            followMapper.updateById(follow1);
        }
        return Result.succ(null);
    }

    private Follow getFollow(Long authorId, Long followerId) {
        return followMapper.selectOne(new QueryWrapper<Follow>()
                .eq("followed", authorId)
                .eq("follower", followerId));
    }

    @Override
    public Result getFollowerList(Long userId, Integer pageNum, Integer pageSize) {
        Page<Follow> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("followed",userId);
        IPage<Follow> data = followMapper.selectPage(page,queryWrapper);
        for (Follow f: data.getRecords()) {
            f.setFollowerInfo(userMapper.selectById(f.getFollower()));
        }

        return Result.succ(data);
    }

    @Override
    public Result getFollowedList(Long userId, Integer pageNum, Integer pageSize) {
        Page<Follow> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower",userId);
        IPage<Follow> data = followMapper.selectPage(page,queryWrapper);
        for (Follow f: data.getRecords()) {
            f.setFollowedInfo(userMapper.selectById(f.getFollowed()));
        }

        return Result.succ(data);
    }

    @Override
    public Result isFollowerFollowed(Long followed, Long follower) {
        return Result.succ(followMapper.selectOne(new QueryWrapper<Follow>().
                eq("followed",followed).
                eq("follower",follower)));
    }
}
