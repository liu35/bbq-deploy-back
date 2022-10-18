package com.group.bbq.controller;


import com.group.bbq.common.dto.AddCommentDto;
import com.group.bbq.common.dto.AddReplyDto;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.Posts;
import com.group.bbq.service.PostsService;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * posts table web controller
 * </p>
 */
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }


    @GetMapping("/index/list")
    public Result indexList(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) Long typeId) {
        return postsService.indexList(pageNum, pageSize, search, typeId);
    }


    @GetMapping("/index/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        return postsService.indexDetail(id);
    }

    @PostMapping("/index/edit")
    public Result edit(@Validated @RequestBody Posts posts) {
        return postsService.indexEdit(posts);
    }


    @GetMapping("/index/approval")
    public Result approval(Long postId, Long userId,Integer click) {
        return postsService.approval(postId, userId,click);
    }

    @GetMapping("/index/views")
    public Result views(Long postId) {
        return postsService.views(postId);
    }

    @DeleteMapping("/index/delete")
    public Result deletePost(Long postId) {
        return postsService.delete(postId);
    }

    @GetMapping("/admin/official/set")
    public Result setOfficial(Long postId) {
        return postsService.setOfficial(postId);
    }

    @GetMapping("/admin/official/delete")
    public Result deleteOfficial(@Validated Long postId) {
        return postsService.deleteOfficial(postId);
    }

    @GetMapping("/admin/top/set")
    public Result setTop(Long postId) {
        return postsService.setTop(postId);
    }

    @GetMapping("/admin/top/delete")
    public Result deleteTop(@Validated Long postId) {
        return postsService.deleteTop(postId);
    }

    @GetMapping("/admin/marrow/set")
    public Result setMarrow(@Validated Long postId) {
        return postsService.setMarrow(postId);
    }

    @GetMapping("/admin/marrow/delete")
    public Result deleteMarrow(Long postId) {
        return postsService.deleteMarrow(postId);
    }

    @GetMapping("/admin/posts/unseen")
    public Result setPostsUnSeen(@Validated Long postId) {
        return postsService.setPostsUnSeen(postId);
    }

    @GetMapping("/admin/posts/seen")
    public Result setPostsSeen(@Validated Long postId) {
        return postsService.setPostsSeen(postId);
    }

    @GetMapping("/admin/index/list")
    public Result allList(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) Long typeId) {
        return postsService.allList(pageNum, pageSize, search, typeId);
    }

    @GetMapping("/comment/list")
    public Result getCommentList(Long postId) {
        return postsService.getCommentList(postId);
    }

    @PostMapping("/comment/add")
    public Result addComment(@Validated @RequestBody AddCommentDto addCommentDto) {
        System.out.println(addCommentDto);
        return postsService.addNewComment(addCommentDto.getPostId(), addCommentDto.getUsername(), addCommentDto.getAvatar(), addCommentDto.getUserId(), addCommentDto.getContent());
    }

    @PostMapping("/reply/add")
    public Result addReply(@Validated @RequestBody AddReplyDto addReplyDto) {
        return postsService.addNewReply(addReplyDto.getCommentId(), addReplyDto.getUsername(),
                addReplyDto.getAvatar(), addReplyDto.getUserId(), addReplyDto.getContent(),
                addReplyDto.getReplyId(), addReplyDto.getReplyName());
    }

}
