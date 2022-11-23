package com.lzg.guli2.edu.entity.vo;

import lombok.Data;

@Data
public class CourseQueryVo {
    private String subjectParentId;
    private String subjectId;
    private String title;
    private String teacherId;
    private String buyCountSort;
    private String gmtCreateSort;
    private String priceSort;
}
