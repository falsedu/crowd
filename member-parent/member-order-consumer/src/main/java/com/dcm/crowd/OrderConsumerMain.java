package com.dcm.crowd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderConsumerMain {


    public static void main(String[] args) {
        SpringApplication.run(OrderConsumerMain.class,args);
    }
}
