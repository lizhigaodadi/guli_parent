package com.lzg.guli2.edu.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lzg.guli2.edu.entity.DemoData;

import java.util.Map;

public class EasyExcelListener extends AnalysisEventListener<DemoData> {

    //每次读取一行数据之后执行
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("当前行数据: {学生姓名:" + demoData.getSname() + " 学生编号:" + demoData.getSno() + "}");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息:" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("excel读取数据结束");
    }
}
