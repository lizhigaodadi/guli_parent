package com.lzg.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String ,Object> data = new HashMap<>();


    //使用链式编程+静态工厂模式

    private R() { }   //内部空参数构造器


    public static R ok() {
        R r = new R();
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        r.setSuccess(true);

        return r;
    }

    public static R error() {
        R r = new R();
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        r.setSuccess(false);

        return r;
    }

    //链式编程

    public R code(Integer code) {
        this.code = code;
        return this;
    }

    public R data(String key,Object value) {
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String,Object> data) {
        this.data = data;
        return this;
    }

    public R message(String message) {
        this.message = message;
        return this;
    }

    public R success(boolean success) {
        this.success = success;
        return this;
    }

}
