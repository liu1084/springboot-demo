package com.proaimltd.web.service.impl;

import com.proaimltd.web.model.entity.Hello;
import com.proaimltd.web.service.IHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.com.proaimltd.web.video.service.impl
 * @author: fengkun.zhao
 * @date: 2019/9/9 17:40
 * @description：TODO
 */
@Service
@Slf4j
public class HelloServiceImpl implements IHelloService {

	@Autowired
	private Hello hello;

	@Override
	public Hello sayHello() {
		log.debug("hello");
		hello.setId(1L);
		hello.setName("zff");
		return hello;
	}
}
