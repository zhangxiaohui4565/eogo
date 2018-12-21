package com.eogo.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhangxh
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.eogo.swagger")
public class Swagger2Config {

    @Autowired
    private SwaggerInfo swaggerInfo;
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo());
        ApiSelectorBuilder select = docket.select();
        if (StringUtils.isNotEmpty(swaggerInfo.getBasePackage())){
            select = select.apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()));

        }
        if (StringUtils.isNotEmpty(swaggerInfo.getAntPath())){
            select = select.paths(PathSelectors.ant(swaggerInfo.getAntPath()));
        }
        return select.build();
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            //页面标题
            .title(swaggerInfo.getTitle())
            //创建人
            .contact(new Contact("Zhangxh", "http://www.eogo.shop", "zhangxh940426@163.com"))
            //版本号
            .license(swaggerInfo.getLicense())
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0")
            //描述
            .description(swaggerInfo.getDescription())
            .build()
            ;
    }

}
