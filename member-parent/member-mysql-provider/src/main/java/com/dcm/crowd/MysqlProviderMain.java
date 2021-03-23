package com.dcm.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.dcm.crowd.mapper")
@EnableEurekaClient
@SpringBootApplication
public class MysqlProviderMain {

    public static void main(String[] args) {
        SpringApplication.run(MysqlProviderMain.class,args);
    }
}
