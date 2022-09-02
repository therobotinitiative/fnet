package com.orbital3d.server.fnet.controller.fnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.SettingsService;
import com.orbital3d.server.fnet.service.item.SesssionKey;

/**
 * Controller for index page information.
 * 
 * @author msiren
 *
 */
@Controller
public class Index {
	/**
	 * DTO class for bringing safe user information to front end. Preventing
	 * password and salt from leaking.
	 * 
	 * @author msiren
	 *
	 */
	// Getters user by framework
	@SuppressWarnings("unused")
	private final static class SafeUser {
		private String userName;
		private Long userId;

		private SafeUser(String userName, Long userId) {
			this.userName = userName;
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public Long getUserId() {
			return userId;
		}

		static SafeUser of(User user) {
			return new SafeUser(user.getUserName(), user.getUserId());
		}
	}

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private SettingsService settingsService;

	@GetMapping("/fnet")
	protected ModelAndView index() {
		ModelAndView modelAndViewv = new ModelAndView("fnet/index");
		fillUserInformation(modelAndViewv);
		return modelAndViewv;
	}

	private void fillUserInformation(ModelAndView modelAndView) {
		modelAndView.addObject("activegroup", sessionService.getCurrentGroup().getGroupId());
		modelAndView.addObject("groups", groupService.getByUser(sessionService.getCurrentUser()));
		modelAndView.addObject("isadmin", groupService.isUserInGroup(sessionService.getCurrentUser(),
				groupService.findByName(settingsService.administratorGroupName()).get()));
		modelAndView.addObject("fnetversion", "Development");
		modelAndView.addObject("user", SafeUser.of(sessionService.getCurrentUser()));
		modelAndView.addObject("user_data", (UserData) sessionService.get(SesssionKey.CURRENT_USER_DATA));
		modelAndView.addObject("rootview", itemService.findRoot(sessionService.getCurrentGroup()).getItemId());
	}
}