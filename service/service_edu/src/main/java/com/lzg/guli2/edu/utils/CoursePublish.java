package com.lzg.guli2.edu.utils;

//定义一个枚举类
public enum CoursePublish {
    PUBLISHED("Normal"),
    UN_PUBLISH("Draft");

    private final String status;
    private CoursePublish(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
