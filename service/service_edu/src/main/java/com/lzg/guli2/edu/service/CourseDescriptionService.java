package com.lzg.guli2.edu.service;

import com.lzg.guli2.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
public interface CourseDescriptionService extends IService<CourseDescription> {

    void removeDescriptionByCourseId(String courseId);

}
