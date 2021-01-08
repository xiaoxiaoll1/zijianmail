package com.zijianmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.zijianmall.product.feign"})
@EnableDiscoveryClient
@SpringBootApplication
public class ProductMain9000 {

    public static void main(String[] args) {
        SpringApplication.run(ProductMain9000.class, args);
    }
}
