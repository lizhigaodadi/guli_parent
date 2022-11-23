package com.lzg.guli2.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Teacher;
import com.lzg.guli2.edu.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("edu/frontTeacher")
public class TeacherFrontController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("getTeacherList/{page}/{limit}")
    public R getTeacherListPage(@PathVariable("page") long page,@PathVariable("limit") long limit) {
        Page<Teacher> teacherPage = new Page<>(page,limit);

        HashMap<String,Object> map = teacherService.getTeacherList(teacherPage);

        return R.ok().data(map);
    }
}
