package com.orbital3d.server.fnet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for application context root.
 * 
 * @author msiren
 *
 */
@RestController
public class Root {

	@GetMapping("/")
	public RedirectView root() {
		return new RedirectView("/login");
	}
}
