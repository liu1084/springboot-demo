package com.proaimltd.web.config;

import com.proaimltd.web.model.entity.Hello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.com.proaimltd.web.video.config
 * @author: fengkun.zhao
 * @date: 2019/9/9 17:44
 * @description：TODO
 */
@Configuration
public class AppBeans {
	@Bean("hello")
	public Hello getHello() {
		return new Hello();
	}
}
