package com.lzg.guli2.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.guli2.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzg.guli2.edu.entity.vo.TeacherWrapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-10-27
 */
public interface TeacherService extends IService<Teacher> {
    void pageQuery(Page<Teacher> teacherPage, TeacherWrapper teacherWrapper);

    List<Teacher> getHotMessage(Integer teacherNum);

    HashMap<String, Object> getTeacherList(Page<Teacher> teacherPage);

}
