package com.eogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhangxh
 * @since 1.0
 * 注册中心启动类
 */
@SpringBootApplication
@EnableEurekaServer
public class EgRegistry {
    public static void main(String[] args) {
        SpringApplication.run(EgRegistry.class);
    }
}
