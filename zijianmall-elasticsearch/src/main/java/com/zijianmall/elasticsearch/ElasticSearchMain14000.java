package com.zijianmall.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaozj
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ElasticSearchMain14000 {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchMain14000.class, args);
    }
}
