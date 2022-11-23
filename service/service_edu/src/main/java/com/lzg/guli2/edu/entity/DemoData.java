package com.lzg.guli2.edu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {

    @ExcelProperty(value = "学生姓名",index = 0)    //下标从0开始
    private String sname;
    @ExcelProperty(value = "学生编号",index = 1)
    private Integer sno;
}
