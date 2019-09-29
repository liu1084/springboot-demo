package com.proaimltd.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web
 * @author: fengkun.zhao
 * @date: 2019/9/9 17:23
 * @description：TODO
 */

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
