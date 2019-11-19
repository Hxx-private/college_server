package com.hxx.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hxx
 */
@SpringBootApplication
@MapperScan("com.hxx.demo.*")
@EnableSwagger2
@CrossOrigin(origins = "*", allowCredentials = "true")

public class CollegeSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeSecurityApplication.class, args);
    }

}
