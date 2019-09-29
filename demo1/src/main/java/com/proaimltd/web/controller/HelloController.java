package com.proaimltd.web.controller;

import com.proaimltd.web.model.entity.Hello;
import com.proaimltd.web.service.IHelloService;
import com.proaimltd.web.video.model.dto.UploadRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web
 * @author: fengkun.zhao
 * @date: 2019/9/9 17:20
 * @description：TODO
 */
@RequestMapping(value = "/hello")
@Controller
//@RestController
@Slf4j
public class HelloController {

	@Autowired
	private IHelloService helloService;

	@GetMapping
	@ResponseBody
	public String hello() {
		return "he";
	}

	@GetMapping("/sayHello")
	@ResponseBody
	public Hello sayHello() {
		return helloService.sayHello();
	}

	@PostMapping("/hello")
	@ResponseBody
	public String hello(@RequestBody String name) {
		String msg = "hello, " + name;
		log.debug(msg);
		return msg;
	}
}
