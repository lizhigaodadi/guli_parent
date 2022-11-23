package com.lzg.guli2.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.guli2.edu.entity.OneSubject;
import com.lzg.guli2.edu.entity.Subject;
import com.lzg.guli2.edu.entity.SubjectData;
import com.lzg.guli2.edu.entity.TwoSubject;
import com.lzg.guli2.edu.handler.SubjectExcelListener;
import com.lzg.guli2.edu.mapper.SubjectMapper;
import com.lzg.guli2.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {



    @Override
    public void addSubject(MultipartFile multipartFile, SubjectService subjectService) {
        try {
            //获取文件的输出流
            InputStream in = multipartFile.getInputStream();

            //读取excel文件数据
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getSubjectTypeList() {
        //查询数据库中的所有一级分类
        QueryWrapper<Subject> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id",0);

        List<Subject> oneSubjectList = baseMapper.selectList(oneQueryWrapper);

        //查询所有的二级分类
        QueryWrapper<Subject> twoQueryWrapper = new QueryWrapper<>();

        twoQueryWrapper.ne("parent_id",0);

        List<Subject> twoSubjectList = baseMapper.selectList(twoQueryWrapper);

        //通过一个hash表来存储第一分类的数据
        HashMap<String,OneSubject> tempOneSubjectHashMap = new HashMap<>();

        //最终返回的结果
        List<OneSubject> finalList = new ArrayList<>();

        //for循环将所有Subject转化为OneSubject
        for (int i=0;i<oneSubjectList.size(); i++ ) {
            Subject subject = oneSubjectList.get(i);
            //将数据进行转化
            OneSubject oneSubject = new OneSubject();
            oneSubject.setTitle(subject.getTitle());
            oneSubject.setId(subject.getId());

            //将数据加入到hash表中，这里可以通过redis进行优化
            tempOneSubjectHashMap.put(oneSubject.getId(),oneSubject);

            //加入到最终结果中去
            finalList.add(oneSubject);
        }


        //这里将二级分类数据一一加入到一级分类之下进行管理

        for (int i=0;i<twoSubjectList.size();i++) {
            Subject subject = twoSubjectList.get(i);

            //对于他的parentId进行，然后分别找到它的父分类
            TwoSubject twoSubject = new TwoSubject();

            twoSubject.setId(subject.getId());
            twoSubject.setTitle(subject.getTitle());

            String parentId = subject.getParentId();

            //通过哈希表来查询到父亲
            OneSubject oneSubject = tempOneSubjectHashMap.get(parentId);

            oneSubject.addTwoSubjects(twoSubject);
        }

        //返回最终结果
        return finalList;
    }
}
