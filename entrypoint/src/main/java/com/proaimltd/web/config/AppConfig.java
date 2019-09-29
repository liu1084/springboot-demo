package com.proaimltd.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.config
 * @author: fengkun.zhao
 * @date: 2019/9/11 13:56
 * @description：TODO
 */

@Configuration
/*@MapperScan("com.proaimltd.web.**.mapper")*/
@ComponentScan(basePackages = {"com.proaimltd.web"})
public class AppConfig {


}
