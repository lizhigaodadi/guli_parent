package com.lzg.guli.order.service;

import com.lzg.commonutils.vo.CourseWebInfoVo;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class RemoteCourseServiceFallback implements RemoteCourseService{


    @Override
    public CourseWebInfoVo remoteCourseInfoById(String courseId) {
        throw new GuliException(20001,"远程调用获取课程信息失败");
    }

}
