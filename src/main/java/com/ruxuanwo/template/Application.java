package com.ruxuanwo.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author ruxuanwo
 */
@ComponentScan(basePackages = "com.ruxuanwo")
@MapperScan(basePackages = {"com.ruxuanwo.**.mapper.**"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
