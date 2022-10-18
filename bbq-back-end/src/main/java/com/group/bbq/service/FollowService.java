package com.group.bbq.service;

import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * user follow table
 * </p>
 */
public interface FollowService extends IService<Follow> {

    Result add(Long authorId, Long followerId);

    Result delete(Long authorId, Long followerId);
}
