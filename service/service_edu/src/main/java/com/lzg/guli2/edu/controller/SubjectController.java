package com.lzg.guli2.edu.controller;


import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.OneSubject;
import com.lzg.guli2.edu.service.SubjectService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@Slf4j
@Api(description = "课程分类接口")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        log.info("执行了一次添加课程分类操作 file:{}",file);
        //通过别人传递过来的service方法进行处理
        subjectService.addSubject(file,subjectService);

        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject() {    //返回所有的课程分类
        List<OneSubject> subjectTypeList = subjectService.getSubjectTypeList();

        return R.ok().data("list",subjectTypeList);
    }


}

