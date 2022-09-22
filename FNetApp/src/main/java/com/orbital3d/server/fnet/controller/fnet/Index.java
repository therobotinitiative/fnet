package com.orbital3d.server.fnet.controller.fnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.SettingsService;
import com.orbital3d.server.fnet.service.item.SesssionKey;
import com.orbital3d.web.security.weblectricfence.authorization.AuthorizationWorker.Authorizer;
import com.orbital3d.web.security.weblectricfence.type.Permission;
import com.orbital3d.web.security.weblectricfence.util.FenceUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
	@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
	@Getter
	private final static class SafeUser {
		private String userName;
		private Long userId;

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

	@Autowired
	private Authorizer authorizer;

	@GetMapping("/fnet")
	protected ModelAndView index() {
		getPermissions();
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
		modelAndView.addObject("canupload",
				FenceUtil.getSubject().isPermitted(Permission.of(FnetPermissions.File.UPLOAD)));
		modelAndView.addObject("cancomment",
				FenceUtil.getSubject().isPermitted(Permission.of(FnetPermissions.Comment.CREATE)));
		modelAndView.addObject("cancreatefoldr",
				FenceUtil.getSubject().isPermitted(Permission.of(FnetPermissions.File.DOWNLOAD)));
	}

	private void getPermissions() {
		if (FenceUtil.getSubject().getPermissions() == null) {
			authorizer.gatherPermissions(FenceUtil.getSubject());
		}
	}
}
