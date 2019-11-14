package com.hxx.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Hxx
 * @Description //TODO Swagger配置类
 * @Date 9:01 2019/10/31
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //项目controller需要被扫描的路径
                .apis(RequestHandlerSelectors.basePackage("com.hxx.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //接口文档的名字
                .title("college  Api")
                //接口文档的描述
                .description("college Api接口文档")
                //服务条款网址
                .termsOfServiceUrl("http://localhost/")
                //接口文档的版本
                .version("1.0。0")
                .build();
    }
}
