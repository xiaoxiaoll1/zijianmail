package com.zijianmall.coupen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CoupenMain6001 {

    public static void main(String[] args) {
        SpringApplication.run(CoupenMain6001.class, args);
    }

}
