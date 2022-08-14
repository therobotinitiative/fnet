package com.orbital3d.server.fnet.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ErrorController {
	@GetMapping("/error/{errorCode}")
	protected RedirectView errorPages(@PathVariable String errorCode) {
		return new RedirectView("error/" + errorCode);
	}
}
