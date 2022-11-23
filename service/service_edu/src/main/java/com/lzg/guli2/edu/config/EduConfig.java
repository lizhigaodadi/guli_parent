package com.lzg.guli2.edu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lzg.guli2.edu.mapper")   //设置扫描mybatis-plus mapper 的路径
public class EduConfig {

    //注入一个逻辑删除的插件
    @Bean
    public ISqlInjector injector() {
        return new LogicSqlInjector();
    }

    //注入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
