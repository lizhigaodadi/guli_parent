package com.lzg.guli.order.service;

import com.lzg.commonutils.vo.CourseWebInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sevice-edu",fallback = RemoteCourseServiceFallback.class)
@Service(value = "remoteCourseService")
public interface RemoteCourseService {

    @GetMapping("/edu/course/remoteCourseInfoById/{courseId}")
    public CourseWebInfoVo remoteCourseInfoById(@PathVariable("courseId") String courseId);

}
