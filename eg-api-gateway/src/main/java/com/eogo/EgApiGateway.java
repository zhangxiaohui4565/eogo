package com.eogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author zhangxh
 * @since 1.0
 * 网关启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class EgApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(EgApiGateway.class);
    }
}
