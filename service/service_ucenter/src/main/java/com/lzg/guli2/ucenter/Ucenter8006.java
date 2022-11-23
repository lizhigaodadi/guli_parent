package com.lzg.guli2.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.lzg")
@SpringBootApplication
//@MapperScan("com.lzg.guli2.ucenter.mapper")
@EnableDiscoveryClient  //开启nacos
@EnableFeignClients
public class Ucenter8006 {

    public static void main(String[] args) {
        SpringApplication.run(Ucenter8006.class,args);
    }
}
