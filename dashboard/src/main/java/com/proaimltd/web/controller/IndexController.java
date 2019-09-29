package com.proaimltd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.controller
 * @author: fengkun.zhao
 * @date: 2019/9/11 14:04
 * @description：TODO
 */
@Controller
public class IndexController {

//	@GetMapping()
//	@ResponseBody
	public String index(){
		return "index";
		}
		}
