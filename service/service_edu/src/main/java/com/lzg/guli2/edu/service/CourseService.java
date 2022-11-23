package com.lzg.guli2.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.guli2.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzg.guli2.edu.entity.vo.CourseInfoVo;
import com.lzg.guli2.edu.entity.vo.CoursePublishVo;
import com.lzg.guli2.edu.entity.vo.CourseQueryVo;
import com.lzg.guli2.edu.entity.vo.CourseWebVo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
public interface CourseService extends IService<Course> {


    String addCourseInfo(CourseInfoVo courseInfoVo) ;

    CourseInfoVo getCourseInfoById(String id);

    void updateById(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVoById(String id);

    void publishCourse(String courseId);

    List<Course> courseQuery(CourseQueryVo courseQueryVo);

    void removeCourseById(String courseId);

    List<Course> getHotMessage(Integer courseNum);

    HashMap<String, Object> getCourseInfoByPage(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseWebVoById(String courseId);
}
