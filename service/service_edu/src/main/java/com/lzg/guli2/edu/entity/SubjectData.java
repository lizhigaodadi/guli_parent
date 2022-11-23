package com.lzg.guli2.edu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data   //对应excel表格的实体类
public class SubjectData {

    @ExcelProperty(value = "一级分类",index = 0)
    private String firstOrderClass;

    @ExcelProperty(value = "二级分类",index = 1)
    private String secondOrderClass;
}
