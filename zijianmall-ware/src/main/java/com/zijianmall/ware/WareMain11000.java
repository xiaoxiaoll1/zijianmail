package com.zijianmall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.zijianmall.ware.feign"})
@SpringBootApplication
public class WareMain11000 {
    public static void main(String[] args) {
        SpringApplication.run(WareMain11000.class, args);
    }
}
