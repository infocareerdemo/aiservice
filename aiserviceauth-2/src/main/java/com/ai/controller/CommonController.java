package com.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	@GetMapping("/welcome")
	public String welcomeMsg() {
		return "Welcome";
		
	}

}
