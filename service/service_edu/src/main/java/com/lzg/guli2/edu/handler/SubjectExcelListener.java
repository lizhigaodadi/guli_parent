package com.lzg.guli2.edu.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.guli2.edu.entity.Subject;
import com.lzg.guli2.edu.entity.SubjectData;
import com.lzg.guli2.edu.service.SubjectService;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private SubjectService subjectService;

    //有参构造
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //空参构造
    public SubjectExcelListener() { }

    //每一行读取时执行方法
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //首先判断一下是否有数据存在
        if (subjectData == null) {
            throw new GuliException(20001,"excel表格数据为空");
        }

        //首先判断一下一级标题是否已经存在库中
        Subject oneSubject = existOneSubject(subjectData.getFirstOrderClass());
        //这里必然会有一个subject

        String pid = oneSubject.getId();
        System.out.println("parent_id: "+pid);

        Subject twoSubject = existTwoSubject(subjectData.getSecondOrderClass(),pid);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private Subject existOneSubject(String className) {
        //通过数据库查询相应数据处理出来
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",className);
        queryWrapper.eq("parent_id",0);

        Subject oneSubject = subjectService.getOne(queryWrapper);

        if (oneSubject == null) {
            log.info("当前一级分类，数据库中不存在，现在添加{}",className);
            //说明数据库中还没有收录这个字段，需要自己添加进去
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(className);
            boolean flag = subjectService.save(subject);
            if (!flag) {
                throw new GuliException(20001,"数据库保存数据失败");
            }
            log.info("当前一级分类的id: {}",subject.getId());

            return subject;
        }


        return oneSubject;
    }


    private Subject existTwoSubject(String className,String pid) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",className);
        queryWrapper.eq("parent_id",pid);

        Subject twoSubject = subjectService.getOne(queryWrapper);

        if (twoSubject == null) {  //发现数据库中不存在该表，插入
            Subject subject = new Subject();
            subject.setTitle(className);
            subject.setParentId(pid);
            boolean flag = subjectService.save(subject);
            if (!flag) {
                throw new GuliException(20001,"数据库插入数据失败");
            }
            return subject;
        }

        return twoSubject;
    }
}
