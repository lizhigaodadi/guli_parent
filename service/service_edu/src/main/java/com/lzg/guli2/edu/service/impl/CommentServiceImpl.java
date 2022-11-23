package com.lzg.guli2.edu.service.impl;

import com.lzg.guli2.edu.entity.Comment;
import com.lzg.guli2.edu.mapper.CommentMapper;
import com.lzg.guli2.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
