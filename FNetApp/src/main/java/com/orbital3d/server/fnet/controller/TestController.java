package com.orbital3d.server.fnet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	private static final Logger LOG = LogManager.getLogger(TestController.class);

	@GetMapping("/test")
	protected String testThis() {
		return "root";
	}

	@GetMapping("/app/index")
	protected String needAuth() {
		return "index";
	}
}
