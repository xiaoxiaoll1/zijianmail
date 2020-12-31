package com.zijianmall.thirdParty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaozj
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ThirdPartyMain13000 {

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyMain13000.class, args);
    }
}
