package com.lzg.guli.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lzg.guli.statistics.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan("com.lzg")     //开启swagger
@EnableScheduling   //开启定时任务
public class StatisticsMain {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsMain.class,args);
    }
}
