package com.lzg.guli2.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.guli2.edu.entity.Course;
import com.lzg.guli2.edu.entity.CourseDescription;
import com.lzg.guli2.edu.entity.vo.CourseInfoVo;
import com.lzg.guli2.edu.entity.vo.CoursePublishVo;
import com.lzg.guli2.edu.entity.vo.CourseQueryVo;
import com.lzg.guli2.edu.entity.vo.CourseWebVo;
import com.lzg.guli2.edu.mapper.CourseMapper;
import com.lzg.guli2.edu.service.ChapterService;
import com.lzg.guli2.edu.service.CourseDescriptionService;
import com.lzg.guli2.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzg.guli2.edu.service.VideoService;
import com.lzg.guli2.edu.utils.CourseInfoConvertToCourseUtils;
import com.lzg.guli2.edu.utils.CoursePublish;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseDescriptionService courseDescriptionService;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ChapterService chapterService;

    @Resource
    private VideoService videoService;


    @Override
    @Transactional   //确保事务
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        //将courseInfo中字段赋值来创建一个Course对象
        Course course = CourseInfoConvertToCourseUtils.convertToCourse(courseInfoVo);
        if (!StringUtils.isEmpty(course.getId())) {
            //发现这是需要进行修改的，进行一个跳转
            this.updateById(courseInfoVo);
            return course.getId();
        }

        //将这个对象存入数据库中
        int insert = baseMapper.insert(course);

        if (insert == 0) {
            throw new GuliException(20001,"课程存储失败");
        }

        //获取课程id
        String id = course.getId();

        String description = courseInfoVo.getDescription();

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(description);
        courseDescription.setId(id);

        boolean save = courseDescriptionService.save(courseDescription);

        if (!save) {
            throw new GuliException(20001,"课程简介存储失败");
        }

        return id;   //返回id
    }

    @Override
    @Transactional
    public CourseInfoVo getCourseInfoById(String id) {

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        //先查询课程本体
        log.info("---------id:{}----------",id);
        Course course = getById(id);
        log.info("course:{}",course);
        //传递属性
        BeanUtils.copyProperties(course,courseInfoVo);

        //查询课程简介
        CourseDescription courseDescription = courseDescriptionService.getById(id);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateById(CourseInfoVo courseInfoVo) {  //通过修改课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseInfoVo.getId());

        //开始进行修改
        int updateNum = baseMapper.updateById(course);
        if(updateNum == 0) {
//            throw new GuliException(20001,"课程信息修改失败");
            log.warn("课程信息未发生修改");
        }


        boolean flag = courseDescriptionService.updateById(courseDescription);
        if (!flag) {
//            throw new GuliException(20001,"课程简介修改失败");
            log.warn("课程简介未发生改变");
        }




    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return courseMapper.getCoursePublishVo(id);
    }

    @Override
    public void publishCourse(String courseId) {
//        UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<>();
//        courseUpdateWrapper.set("status",CoursePublish.PUBLISHED.getStatus())
//                .eq("id",courseId);
//        baseMapper.update(null,courseUpdateWrapper);

        Course course = new Course();
        course.setId(courseId);
        course.setStatus(CoursePublish.PUBLISHED.getStatus());

        baseMapper.updateById(course);

    }

    @Override
    public List<Course> courseQuery(CourseQueryVo courseQueryVo) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseQueryVo.getTitle())) {
            courseQueryWrapper.like("title", courseQueryVo.getTitle());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
            courseQueryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {
            courseQueryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQueryVo.getTeacherId())) {
            courseQueryWrapper.eq("teacher_id",courseQueryVo.getTeacherId());
        }

        return baseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public void removeCourseById(String courseId) {
        //先删除小节
        videoService.removeByCourseId(courseId);

        //删除章节
        chapterService.removeChapterByCourseId(courseId);

        //删除课程简介
        courseDescriptionService.removeDescriptionByCourseId(courseId);

        //最后删除课程本体
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new GuliException(20001,"课程删除异常");
        }

    }

    @Override
    public List<Course> getHotMessage(Integer courseNum) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        String lastSql = "limit " + courseNum;
        log.info("lastSql:{}",lastSql);
        courseQueryWrapper.orderByAsc("id").last(lastSql);

        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);

        return courseList;


    }

    @Override
    public HashMap<String, Object> getCourseInfoByPage(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

        if (courseQueryVo != null) {
            //判断一下是否有一级分类
            if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {  //发现有
                courseQueryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
            }

            //判断一下是否有二级分类
            if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
                courseQueryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
            }

            //判断一下通过什么来排序
            if (StringUtils.isEmpty(courseQueryVo.getPriceSort())) {
                courseQueryWrapper.orderByDesc("price");
            }

            if (StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {
                courseQueryWrapper.orderByDesc("buy_count");
            }

            if (StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {
                courseQueryWrapper.orderByDesc("gmt_create");
            }
        }

        //开始查询数据
        baseMapper.selectPage(coursePage,courseQueryWrapper);

        List<Course> records = coursePage.getRecords();
        long total = coursePage.getTotal();

        long current = coursePage.getCurrent();

        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();



        HashMap<String,Object> result = new HashMap<>();

        result.put("items",records);
        result.put("current",current);
        result.put("pages",pages);
        result.put("total",total);
        result.put("size",size);
        result.put("hasNext",hasNext);
        result.put("hasPrevious",hasPrevious);


        return result;
    }

    @Override
    public CourseWebVo getCourseWebVoById(String courseId) {
        //手写sql语句
        return baseMapper.getCourseWebVoById(courseId);
    }
}
