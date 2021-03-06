package com.xh.srb.core;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@MapperScan({"com.xh.srb.core.mapper"})
@ComponentScan({"com.xh.srb", "com.xh.common"})
@EnableDiscoveryClient
public class ServiceCoreApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ServiceCoreApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
