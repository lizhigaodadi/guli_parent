package com.lzg.sevicebase.exceptionhandler;

import com.lzg.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)  //捕获全局所有抛出的异常
    public R globalExceptionHandler(Exception e) {
        //打印堆栈轨迹
        e.printStackTrace();

        return R.error().message("全局异常处理");
    }

    @ExceptionHandler(GuliException.class)
    public R guliExceptionHandler(GuliException e) {
        e.printStackTrace();

        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
