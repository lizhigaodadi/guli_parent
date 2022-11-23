package com.lzg.guli2.edu.controller.front;

import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Course;
import com.lzg.guli2.edu.entity.Teacher;
import com.lzg.guli2.edu.service.CourseService;
import com.lzg.guli2.edu.service.TeacherService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/edu/front")
public class FrontIndexController {

    @Resource
    private TeacherService teacherService;


    @Resource
    private CourseService courseService;

    @GetMapping("index/{teacherNum}/{courseNum}")
    public R getHotMessage(@Nullable @PathVariable("teacherNum") @ApiParam(required = false,name = "teacherNum",value = "热门老师数量" ) Integer teacherNum,
                           @Nullable @PathVariable("courseNum") @ApiParam(required = false,name = "courseNum",value = "热门课程数量" ) Integer courseNum) {   //查询热门信息
        //打印日志信息
        log.info("teacherNum:{},courseNum:{}",teacherNum,courseNum
        );


        List<Teacher> teacherList =teacherService.getHotMessage(teacherNum);

        List<Course> courseList = courseService.getHotMessage(courseNum);

        return R.ok().data("teacherList",teacherList).data("courseList",courseList);
    }

}
