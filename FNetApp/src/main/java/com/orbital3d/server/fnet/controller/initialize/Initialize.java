package com.orbital3d.server.fnet.controller.initialize;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.PermissionEntity;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.MappingService;
import com.orbital3d.server.fnet.service.PermissionService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.UserDataService;
import com.orbital3d.server.fnet.service.UserService;

/**
 * Controller for initializing Fnet. Requires "enable-init" profile yo be
 * active.
 * 
 * @author msiren
 *
 */
@Profile("enable-init")
@RestController
public class Initialize {
	@Autowired
	private UserService userService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private MappingService mappingService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private PermissionService permissionService;

	@GetMapping("/initialize")
	protected ModelAndView initialize() {
		ModelAndView maodelAndView = new ModelAndView("initialize");
		if (userService.findUserByName("administrator").isPresent()) {
			maodelAndView.addObject("initialized", Boolean.TRUE);
		}
		return maodelAndView;
	}

	@PostMapping("/initialize")
	public RedirectView setPassword(@RequestParam String password) throws NoSuchAlgorithmException {
		if (userService.findUserByName("administrator").isEmpty()) {
			// initialize only if not present
			Group adminGroup = groupService.create("administrator");
			// do the pwd stuff
			User adminUser = userService.createUser("administrator", password);
			adminUser = userService.save(adminUser);
			userDataService.save(UserData.of(adminUser.getUserId(), "Admin", "Admin", "admin@fnet.com", new Date(),
					"n/a", adminGroup.getGroupId(), new Date(), new Date()));
			mappingService.addUser(adminUser, adminGroup);
			permissionService.add(PermissionEntity.of(adminUser.getUserId(), "*"));
			sessionService.setCurrentUser(adminUser);
			sessionService.setCurrentGroup(adminGroup);
			itemService.createRoot(adminGroup, adminUser.getUserId());
		}
		return new RedirectView("/login");
	}
}
