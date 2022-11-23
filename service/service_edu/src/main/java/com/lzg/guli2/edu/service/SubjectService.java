package com.lzg.guli2.edu.service;

import com.lzg.guli2.edu.entity.OneSubject;
import com.lzg.guli2.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
public interface SubjectService extends IService<Subject> {

    void addSubject(MultipartFile multipartFile,SubjectService subjectService);

    List<OneSubject> getSubjectTypeList();
}
