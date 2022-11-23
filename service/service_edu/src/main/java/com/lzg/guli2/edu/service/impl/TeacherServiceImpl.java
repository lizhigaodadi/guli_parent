package com.lzg.guli2.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.guli2.edu.entity.Teacher;
import com.lzg.guli2.edu.entity.vo.TeacherWrapper;
import com.lzg.guli2.edu.mapper.TeacherMapper;
import com.lzg.guli2.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-10-27
 */
@Service
@Slf4j
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    /**
     *
     * @param teacherPage  分页插件对象
     * @param teacherWrapper  封装了所需要查询的条件
     */
    @Override
    public void pageQuery(Page<Teacher> teacherPage, TeacherWrapper teacherWrapper) {

        //首先判断是否为空
        if (teacherWrapper == null) {
            teacherMapper.selectPage(teacherPage,null);
            return;
        }
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(teacherWrapper.getName())) {
            //存在name字段，进行模糊查询
            teacherQueryWrapper.like("name",teacherWrapper.getName());
        }

        if (!StringUtils.isEmpty(teacherWrapper.getLevel())) {
            //存在level字段
            teacherQueryWrapper.eq("level",teacherWrapper.getLevel());
        }

        if (!StringUtils.isEmpty(teacherWrapper.getBegin())) {
            //存在begin字段
            teacherQueryWrapper.gt("gmt_create",teacherWrapper.getBegin());
        }

        if (!StringUtils.isEmpty(teacherWrapper.getEnd())) {
            // 存在end字段
            teacherQueryWrapper.lt("gmt_create",teacherWrapper.getEnd());
        }

        //以创建时间来降序排序
        teacherQueryWrapper.orderByDesc("gmt_create");

        //最后查询
        teacherMapper.selectPage(teacherPage,teacherQueryWrapper);

    }

    @Override
    public List<Teacher> getHotMessage(Integer teacherNum) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        String lastSql = "limit " + teacherNum;
        log.info("lastSql:{}",lastSql);
        teacherQueryWrapper.orderByDesc("id").last(lastSql);


        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);

        return teachers;
    }

    @Override
    public HashMap<String, Object> getTeacherList(Page<Teacher> teacherPage) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");

        baseMapper.selectPage(teacherPage,teacherQueryWrapper);

        //从page中提取数据
        List<Teacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();



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
}
