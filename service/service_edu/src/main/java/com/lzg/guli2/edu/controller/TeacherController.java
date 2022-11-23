package com.lzg.guli2.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Course;
import com.lzg.guli2.edu.entity.Teacher;
import com.lzg.guli2.edu.entity.vo.TeacherWrapper;
import com.lzg.guli2.edu.service.CourseService;
import com.lzg.guli2.edu.service.TeacherService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 *
 *
 * @author testjava
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/edu/teacher")
@Slf4j
@CrossOrigin
@Api(description = "讲师管理")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseService courseService;

    //一个rest风格的接口
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R finalAll() {
        //暂时没有任何查询条件，查询所有人
        List<Teacher> list = teacherService.list(null);
        if (list == null) {
            return R.error().message("查询为空");
        }
        return R.ok().data("items",list);
    }



    @ApiOperation("通过id来移除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable("id") String id) {
        boolean flag = teacherService.removeById(id);

        return R.ok().data("flag",true);
    }

    //通过分页插件来实现分页查询分页查询
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(@ApiParam(name = "page",value = "当前页") @PathVariable("page") Long page,
                      @ApiParam(name = "limit",value = "每页数量") @PathVariable("limit") Long limit) {
        Page<Teacher> teacherPage = new Page<>(page,limit);

        //分页查询
        teacherService.page(teacherPage,null);

        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();

        //封装起来
        return R.ok().data("total",total).data("rows",records);
    }



    @PostMapping("teacherQuery/{page}/{limit}")
    @ApiOperation(value = "条件分页查询讲师列表")
    public R pageTeacherQuery(@ApiParam(name = "page",value = "当前页") @PathVariable("page") Long page,
                              @ApiParam(name = "limit",value = "每页数量") @PathVariable("limit") Long limit,
                              @RequestBody TeacherWrapper teacherWrapper) {


        System.out.println(teacherWrapper);
        //封装一个page对象
        Page<Teacher> teacherPage = new Page<>(page,limit);

        teacherService.pageQuery(teacherPage,teacherWrapper);

        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();

        //封装起来
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     *   在数据库中新增一个讲师信息
     * @param teacher  前端传递来的json对象
     * @return
     */
    @PostMapping("addTeacher")
    @ApiOperation(value = "新增讲师")
    public R addTeacher(@ApiParam(name = "teacher",value = "讲师对象",required = true) @RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    /**
     *
     * @param id  要查询讲师的id
     * @return
     */
    @ApiOperation(value = "根据id来查询讲师信息")
    @GetMapping("selectById/{id}")
    public R selectTeacherById(@ApiParam(value = "讲师id",name = "id",required = true) @PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            return R.error().message("id信息为null");
        }

        Teacher target = teacherService.getById(id);
        if (target == null) {
            return R.error().message("未查询到相关讲师");
        }
        return R.ok().data("item",target);
    }



    @ApiOperation(value = "通过id来进行修改")
    @PutMapping("{id}")
    public R updateById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable("id") String id,
                        @ApiParam(name = "teacher",value = "新的讲师对象",required = true) @RequestBody Teacher teacher) {
        if (teacher == null) return R.error().message("讲师信息为空");

        //先将id赋值给新的teacher 这样mp才能正确的修改信息
        teacher.setId(id);

        teacherService.updateById(teacher);
        return R.ok();
    }


    @GetMapping("getTeacherInfoFrontById/{teacherId}")
    public R getTeacherInfoFrontById(@PathVariable("teacherId") String teacherId) {
        log.info("-----------------teacherId:{}------------------",teacherId);
        //从数据库中查询讲师出来
        Teacher teacher = teacherService.getById(teacherId);
        log.info("teacher:{}",teacher);


        //从数据库中查询出讲师所讲授的所有课程
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(courseQueryWrapper);

        log.info("courseList:{}",courseList);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }




}

