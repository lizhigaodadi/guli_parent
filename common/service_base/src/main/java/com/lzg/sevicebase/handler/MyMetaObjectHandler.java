package com.lzg.sevicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {  //插入数据时执行
        this.setFieldValByName("gmtCreate",new Date(),metaObject);  //设置创建时间
        this.setFieldValByName("gmtModified",new Date(),metaObject);  //设置修改时间
    }

    @Override
    public void updateFill(MetaObject metaObject) { //修改数据时执行
        this.setFieldValByName("gmtModified",new Date(),metaObject);   //设置修改时间
    }
}
