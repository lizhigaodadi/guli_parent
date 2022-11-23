package com.lzg.guli2.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lzg"})   //为了扫描到swagger组件
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
public class EduMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(EduMain8001.class,args);
    }
}
