package com.lzg.guli2.edu.mapper;

import com.lzg.guli2.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzg.guli2.edu.entity.vo.CoursePublishVo;
import com.lzg.guli2.edu.entity.vo.CourseWebVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo getCoursePublishVo(@Param("courseId") String courseId);

    CourseWebVo getCourseWebVoById(@Param("courseId") String courseId);
}
