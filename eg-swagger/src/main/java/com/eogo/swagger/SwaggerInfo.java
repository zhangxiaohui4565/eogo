package com.eogo.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangxh
 * @since 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerInfo {
    private String basePackage;
    private String antPath ;
    private String anyPath;
    private String title = "EOGO后台商品管理API";
    private String description = "API 描述";
    private String license = "Apache 2.0";


}
