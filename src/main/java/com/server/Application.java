package com.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:00
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.server.dao"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
