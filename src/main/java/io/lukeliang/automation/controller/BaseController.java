package io.lukeliang.automation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("abc")
public class BaseController {
	
	@Autowired
	private ConfigurableApplicationContext context;
	
	@RequestMapping("test")
	@ResponseBody
	public String test() {
		System.out.println("hello");
		return "hi";
	}

}
