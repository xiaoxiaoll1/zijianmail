package com.zijianmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zijianmall.member.feign")
@SpringBootApplication
public class MemberMain7000 {

    public static void main(String[] args) {
        SpringApplication.run(MemberMain7000.class, args);
    }

}
