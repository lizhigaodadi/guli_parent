package com.lzg.guli2.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class CourseInfoVo {
    @ApiModelProperty(value = "课程简介")
    private String description;    //课程信息的描述

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程讲师id")
    private String teacherId;

    @ApiModelProperty(value = "课程二级分类id")
    private String subjectId;

    @ApiModelProperty(value = "课程一级分类id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置0则可以免费观看")
    private BigDecimal price;   //精度为0.01

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

}
