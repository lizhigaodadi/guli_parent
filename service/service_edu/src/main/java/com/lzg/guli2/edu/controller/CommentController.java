package com.lzg.guli2.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Comment;
import com.lzg.guli2.edu.entity.vo.PostCommentInfoVo;
import com.lzg.guli2.edu.service.CommentService;
import com.lzg.guli2.edu.service.impl.CommentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-19
 */
@RestController
@RequestMapping("/edu/comment")
@CrossOrigin
@Slf4j
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/pageComment/{page}/{limit}")
    public R pageComment(@PathVariable("page") long page,
                         @PathVariable("limit") long limit) {
        Page<Comment> commentPage = new Page<>(page,limit);
        //开始进行分页查询
        commentService.page(commentPage,null);

        //开始收集信息
        return R.ok().data("total",commentPage.getTotal())
                .data("items",commentPage.getRecords())
                .data("hasPrevious",commentPage.hasPrevious())
                .data("hasNext",commentPage.hasNext())
                .data("current",commentPage.getCurrent());
    }


    @Resource
    private CommentClient commentClient;  //openFeign远程调用接口

    @PostMapping("comment")
    public R comment(@RequestBody(required = false) PostCommentInfoVo postCommentInfoVo,HttpServletRequest request) {
        //获取token信息
        String token = request.getHeader("token");
        log.info("token:{}",token);

        R userInfo = commentClient.remoteParseToken(token);
        HashMap<String,String> user = (HashMap<String, String>) userInfo.getData().get("user");

        //再数据库中添加一条评论信息
        Comment newComment = new Comment();
        newComment.setContent(postCommentInfoVo.getComment());
        newComment.setTeacherId(postCommentInfoVo.getTeacherId());
        newComment.setCourseId(postCommentInfoVo.getCourseId());

        newComment.setAvatar(user.get("avatar"));
        newComment.setNickname(user.get("nickname"));
        newComment.setMemberId(user.get("id"));

        //插入数据库中
        commentService.save(newComment);
        return R.ok();
    }


    @GetMapping("testRemote")
    public R testRemote() {
        String token =  "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2Njg4NjUxMTYsImV4cCI6MTY2ODk1MTUxNiwiaWQiOiIxNTkyNzE0NzI1NzM5ODIzMTA2Iiwibmlja25hbWUiOiJsaXpoaWdhbyJ9.Say1OhDprRdw7DoKw9jeEGkTrg2faOVyXjCD0kg5v3A";
        R userInfo = commentClient.remoteParseToken(token);
        return userInfo;
    }

}

