package com.group.bbq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.ArticleType;
import com.group.bbq.entity.PostApproval;
import com.group.bbq.entity.Posts;
import com.group.bbq.mapper.ArticleTypeMapper;
import com.group.bbq.mapper.PostApprovalMapper;
import com.group.bbq.mapper.PostsMapper;
import com.group.bbq.mapper.UserMapper;
import com.group.bbq.service.PostsService;
import com.group.bbq.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * posts table service impl
 * </p>
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final PostsMapper postsMapper;
    private final ArticleTypeMapper articleTypeMapper;
    private final UserMapper userMapper;
    private final PostApprovalMapper postApprovalMapper;

    @Autowired
    public PostsServiceImpl(PostsMapper postsMapper, ArticleTypeMapper articleTypeMapper, UserMapper userMapper , PostApprovalMapper postApprovalMapper) {
        this.postsMapper = postsMapper;
        this.articleTypeMapper = articleTypeMapper;
        this.userMapper = userMapper;
        this.postApprovalMapper = postApprovalMapper;
    }


    @Override
    public Result indexList(Integer pageNum, Integer pageSize, String search, Long typeId) {

        Page<Posts> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Posts> postsQueryWrapper = new QueryWrapper<>();

        if (ObjectUtil.isEmpty(search)) {
            postsQueryWrapper.eq("is_delete", 0)
                    .eq("audit_state", "PASS")
                    .orderByDesc("top").orderByDesc("marrow").orderByDesc("official")
                    .orderByDesc("create_time");
        } else {
            postsQueryWrapper.eq("is_delete", 0)
                    .eq("audit_state", "PASS")
                    .like("title", search)
                    .or()
                    .like("markdown_content", search)
                    .or()
                    .like("html_content", search)
                    .orderByDesc("top").orderByDesc("marrow").orderByDesc("official")
                    .orderByDesc("create_time");
        }

        if (ObjectUtil.isNotEmpty(typeId)) {
            postsQueryWrapper.eq("type_id", typeId);
        }

        IPage<Posts> pageData = this.page(page, postsQueryWrapper);
        for (Posts posts: pageData.getRecords()) {
            posts.setAuthorName(userMapper.selectById(posts.getAuthorId()).getUsername());
            posts.setType(articleTypeMapper.selectById(posts.getTypeId()).getName());
        }

        return Result.succ(pageData);
    }

    @Override
    public Result indexDetail(Long id) {
        Posts posts = postsMapper.selectById(id);
        posts.setAuthorName(userMapper.selectById(posts.getAuthorId()).getUsername());
        posts.setType(articleTypeMapper.selectById(posts.getTypeId()).getName());
        Assert.notNull(posts, "The post has since been deleted");
        return Result.succ(posts);
    }

    @Override
    public Result indexEdit(Posts posts) {
        Posts p = null;
        if (posts.getId() != null) {
            p = postsMapper.selectById(posts.getId());
            p.setUpdateTime(LocalDateTime.now());

            Assert.isTrue(p.getAuthorId().longValue() == posts.getAuthorId().longValue(), "No permission to edit");
        } else {
            p = new Posts();
            p.setCreateTime(LocalDateTime.now());
            p.setUpdateTime(LocalDateTime.now());
            p.setAuditState("PASS");
            p.setContentType("MARKDOWN");
        }
        BeanUtil.copyProperties(posts, p, "id", "createTime", "auditState", "contentType");


        ArticleType articleType = articleTypeMapper.selectById(p.getTypeId());
        if (ObjectUtil.isEmpty(articleType)) {
            return Result.fail("the type is not exist");
        }

        articleType.setRefCount(articleType.getRefCount() + 1);
        articleTypeMapper.updateById(articleType);

        Assert.isTrue(this.saveOrUpdate(p), "Post operation failed");
        return Result.succ(p);
    }

    @Override
    public Result approval(Long postId, Long userId,Integer click) {

        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }

        List<PostApproval> postApprovals = postApprovalMapper.selectList(
                new QueryWrapper<PostApproval>().eq("post_id", postId).eq("user_id", userId));
        if (ObjectUtil.isNotEmpty(postApprovals)) {
            return Result.fail("you have already approved");
        }


        if (click == 0) {
            posts.setApprovals(posts.getApprovals() + 1);
            PostApproval postApproval = new PostApproval();
            postApproval.setPostId(postId);
            postApproval.setUserId(userId);
            postApprovalMapper.insert(postApproval);
        }else if(click == 1) {
            posts.setApprovals(posts.getApprovals() -1);
            postApprovalMapper.delete(new QueryWrapper<PostApproval>().eq("post_id", postId).eq("user_id", userId));
        }
        postsMapper.updateById(posts);
        return Result.succ("approval");
    }

    @Override
    public Result views(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }

        posts.setViews(posts.getViews() + 1) ;
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result delete(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }

        posts.setIsDelete(1);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result setOfficial(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setOfficial(1);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result deleteOfficial(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setOfficial(0);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result setTop(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setTop(1);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result deleteTop(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setTop(0);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result setMarrow(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setMarrow(1);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result deleteMarrow(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setMarrow(0);
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result setPostsUnSeen(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setAuditState("UNSEEN");
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result setPostsSeen(Long postId) {
        Posts posts = postsMapper.selectById(postId);
        if (ObjectUtil.isEmpty(posts)) {
            return Result.fail("the posts is not exist");
        }
        posts.setAuditState("PASS");
        postsMapper.updateById(posts);
        return Result.succ(null);
    }

    @Override
    public Result allList(Integer pageNum, Integer pageSize, String search, Long typeId) {
        Page<Posts> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Posts> postsQueryWrapper = new QueryWrapper<>();

        if (ObjectUtil.isEmpty(search)) {
            postsQueryWrapper.eq("is_delete", 0)
                    .orderByDesc("top").orderByDesc("marrow").orderByDesc("official")
                    .orderByDesc("create_time");
        } else {
            postsQueryWrapper.eq("is_delete", 0)
                    .like("title", search)
                    .or()
                    .like("markdown_content", search)
                    .or()
                    .like("html_content", search)
                    .orderByDesc("top").orderByDesc("marrow").orderByDesc("official")
                    .orderByDesc("create_time");
        }

        if (ObjectUtil.isNotEmpty(typeId)) {
            postsQueryWrapper.eq("type_id", typeId);
        }

        IPage<Posts> pageData = this.page(page, postsQueryWrapper);
        for (Posts posts: pageData.getRecords()) {
            posts.setAuthorName(userMapper.selectById(posts.getAuthorId()).getUsername());
            posts.setType(articleTypeMapper.selectById(posts.getTypeId()).getName());
        }

        return Result.succ(pageData);
    }
}
