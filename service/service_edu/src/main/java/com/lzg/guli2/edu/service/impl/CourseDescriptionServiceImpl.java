package com.lzg.guli2.edu.service.impl;

import com.lzg.guli2.edu.entity.CourseDescription;
import com.lzg.guli2.edu.mapper.CourseDescriptionMapper;
import com.lzg.guli2.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

    @Override
    public void removeDescriptionByCourseId(String courseId) {
//        QueryWrapper<CourseDescription> courseDescriptionQueryWrapper = new QueryWrapper<>();
//        courseDescriptionQueryWrapper.eq("id",courseId);

        baseMapper.deleteById(courseId);
    }
}
