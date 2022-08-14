package com.orbital3d.server.fnet.controller.fnet.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

	/**
	 * @return Model and view for the administrator page
	 */
	@GetMapping("/fnet/view/admin")
	protected ModelAndView adminView() {
		return new ModelAndView("view/admin");
	}

	/**
	 * @return Model and view for the main page
	 */
	@GetMapping("/fnet/view/main")
	protected ModelAndView mainView() {
		return new ModelAndView("view/main");
	}
}
