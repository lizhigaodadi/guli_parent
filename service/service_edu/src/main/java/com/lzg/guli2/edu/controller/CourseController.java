package com.lzg.guli2.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.commonutils.R;
import com.lzg.commonutils.vo.CourseWebInfoVo;
import com.lzg.guli2.edu.entity.Course;
import com.lzg.guli2.edu.entity.vo.CourseInfoVo;
import com.lzg.guli2.edu.entity.vo.CoursePublishVo;
import com.lzg.guli2.edu.entity.vo.CourseQueryVo;
import com.lzg.guli2.edu.entity.vo.CourseWebVo;
import com.lzg.guli2.edu.entity.vo.chapter.ChapterVo;
import com.lzg.guli2.edu.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/edu/course")
@Api(description = "课程管理接口")
@Slf4j
@CrossOrigin
public class CourseController {
    @Resource
    private CourseService courseService;

    @Resource
    private ChapterService chapterService;



    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {  //传递来一个json对象

        log.info("courseInfo:{}",courseInfoVo);

        String cid = courseService.addCourseInfo(courseInfoVo);

        return R.ok().data("classId",cid);
    }


    //根据课程id来查询所有课程信息
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable("id") String id) {

        CourseInfoVo courseInfoVo =  courseService.getCourseInfoById(id);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        courseService.updateById(courseInfoVo);
        return R.ok();
    }


    @GetMapping("getCoursePublishVo/{id}")
    public R getCoursePublishVo(@PathVariable("id") String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);

        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id) {
        courseService.publishCourse(id);
        return R.ok();
    }

    //通过分页插件来查询课程信息
    @GetMapping("getCourse/{page}/{limit}")
    public R getCourse(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit) {
        Page<Course> coursePage = new Page<>(page,limit);  //当前页和每页查询出来的数量

        courseService.page(coursePage,null);

        List<Course> list = coursePage.getRecords();
        long total = coursePage.getTotal();

        return R.ok().data("list",list).data("total",total);

    }


    @PostMapping("courseQuery")
    public R courseQuery(@RequestBody CourseQueryVo courseQueryVo) {
        //模胡查询
        List<Course> list = courseService.courseQuery(courseQueryVo);
        return R.ok().data("list",list);
    }


    @GetMapping("removeCourseByCourseId/{courseId}")
    public R removeCourseByCourseId(@PathVariable("courseId") String courseId) {
        courseService.removeCourseById(courseId);

        return R.ok();
    }



    @PostMapping("pageCourse/{page}/{limit}")
    public R pageList(@PathVariable("page") long page,
                      @PathVariable("limit") long limit,
                      @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<Course> coursePage = new Page<>(page,limit);

        HashMap<String,Object> map = courseService.getCourseInfoByPage(coursePage,courseQueryVo);

        return R.ok().data("courseInfo",map);
    }


    //通过课程id来查询课程详细信息
    @GetMapping("getByCourseInfoById/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable("courseId") String courseId) {
        log.info("courseId: {}",courseId);

        //获取课程详细信息
        CourseWebVo courseWebVo = courseService.getCourseWebVoById(courseId);

        //查询该课程下所有章节和小节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);


        return R.ok().data("chapterVoList",chapterVoList).data("courseWebVo",courseWebVo);
    }


    @GetMapping("remoteCourseInfoById/{courseId}")
    public CourseWebInfoVo remoteCourseInfoById(@PathVariable("courseId") String courseId) {
        Course course = courseService.getById(courseId);

        CourseWebInfoVo courseWebInfoVo = new CourseWebInfoVo();

        //进行数据的迁移
        BeanUtils.copyProperties(course,courseWebInfoVo);

        return courseWebInfoVo;
    }



}

