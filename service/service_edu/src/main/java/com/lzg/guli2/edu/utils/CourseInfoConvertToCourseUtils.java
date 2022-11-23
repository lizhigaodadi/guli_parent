package com.lzg.guli2.edu.utils;

import com.lzg.guli2.edu.entity.Course;
import com.lzg.guli2.edu.entity.vo.CourseInfoVo;

public class CourseInfoConvertToCourseUtils {
    public static Course convertToCourse(CourseInfoVo courseInfoVo) {
        Course course = new Course();

        course.setId(courseInfoVo.getId());
        course.setCover(courseInfoVo.getCover());
        course.setLessonNum(courseInfoVo.getLessonNum());
        course.setPrice(courseInfoVo.getPrice());
        course.setTeacherId(courseInfoVo.getTeacherId());
        course.setSubjectId(courseInfoVo.getSubjectId());
        course.setTitle(courseInfoVo.getTitle());
        course.setSubjectParentId(courseInfoVo.getSubjectParentId());


        return course;
    }
}
