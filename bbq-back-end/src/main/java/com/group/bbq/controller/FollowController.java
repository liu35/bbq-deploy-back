package com.group.bbq.controller;


import com.group.bbq.common.lang.Result;
import com.group.bbq.mapper.FollowMapper;
import com.group.bbq.service.FollowService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * user follow table
 * </p>
 */
@RestController
@RequestMapping("/follow")
public class FollowController {


    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/add")
    public Result follow(Long authorId, Long followerId) {
        return followService.add(authorId, followerId);
    }

    @DeleteMapping("/delete")
    public Result unfollow(Long authorId, Long followerId) {
        return followService.delete(authorId,followerId);
    }
}
