package com.orbital3d.server.fnet.controller.fnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.item.SesssionKey;

@Controller
public class IndexController {
	@Autowired
	private SessionService sessionService;

	@GetMapping("/fnet")
	protected ModelAndView index() {
		ModelAndView modelAndViewv = new ModelAndView("fnet/index");
		fillUserInformation(modelAndViewv);
		return modelAndViewv;
	}

	private void fillUserInformation(ModelAndView mav) {
		mav.addObject("username", sessionService.getCurrentUser().getUserName());
		mav.addObject("firstname", ((UserData) sessionService.get(SesssionKey.CURRENT_USER_DATA)).getFirstName());
		mav.addObject("activegroup", sessionService.getCurrentGroup().getGroupId());
		mav.addObject("isadmin", Boolean.TRUE);
		mav.addObject("fnetversion", "Development");
		mav.addObject("user", sessionService.getCurrentUser());
	}
}
