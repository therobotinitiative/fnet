package com.orbital3d.server.fnet.controller.administrative;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Administrative index page controller.
 * 
 * @author msiren
 *
 */
@Controller
public class AdminIndex {

	/**
	 * Returns the name of the administrator index page and menu items in the model.
	 * 
	 * @param model Model for the view
	 * @return View name
	 */
	@GetMapping("/fnet/admin/main")
	protected String adminIndex(Model model) {
		Map<String, String> menu = new LinkedHashMap<>();
		menu.put("#!/admin?view=user", "Users");
		menu.put("#!/admin?view=group", "Groups");
		model.addAttribute("menu", menu);
		return "view/admin";
	}
}
