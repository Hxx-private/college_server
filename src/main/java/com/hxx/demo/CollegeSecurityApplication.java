package com.hxx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hxx
 */
@EnableSwagger2
@CrossOrigin(origins = "*", allowCredentials = "true")
@SpringBootApplication
public class CollegeSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeSecurityApplication.class, args);
    }

}
