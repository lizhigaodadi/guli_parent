package com.lzg.guli2.edu.entity.vo;

import lombok.Data;

@Data
public class PostCommentInfoVo {

    private String comment;   //评论内容
    private String teacherId;  //老师id
    private String courseId;
}
