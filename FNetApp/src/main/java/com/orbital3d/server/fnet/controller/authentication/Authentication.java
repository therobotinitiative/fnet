package com.orbital3d.server.fnet.controller.authentication;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.UserDataService;
import com.orbital3d.server.fnet.service.UserService;
import com.orbital3d.server.fnet.service.item.SesssionKey;
import com.orbital3d.web.security.weblectricfence.authentication.token.UsernamePasswordToken;
import com.orbital3d.web.security.weblectricfence.exception.AuthenticationException;
import com.orbital3d.web.security.weblectricfence.util.FenceUtil;
import com.orbital3d.web.security.weblectricfence.util.HashUtil;

/**
 * Controller for authentication related operations.
 * 
 * @author msiren
 *
 */
@Controller
public class Authentication {
	private static final Logger LOG = LoggerFactory.getLogger(Authentication.class);

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	/**
	 * @return Model and view for the login page
	 */
	@GetMapping("/login")
	protected ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("reason", "");
		try {
			modelAndView.addObject("token", createToken());
		} catch (NoSuchAlgorithmException e) {
			// Fail silently
		}
		return modelAndView;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param type        Redirect based on the authentication status
	 * @param verifyToken
	 * @return
	 * @throws AuthenticationException
	 * @throws LoginException
	 */
	@PostMapping("/login")
	protected RedirectView login(@RequestParam(name = "username", required = false) String userName,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "type", required = true) int type,
			@RequestParam(name = "_token", required = true) String verifyToken)
			throws AuthenticationException, LoginException {
		// Perform login
		FenceUtil.login(UsernamePasswordToken.of(userName, password));
		LOG.info("{} logged in", userName);
		// Update login information
		Optional<User> user = userService.findUserByName(userName);
//		FenceUtil.setSubject( user.get());
		Optional<UserData> userData = userDataService.getById(user.get().getUserId());
		userDataService.updateLastLogin(user.get());
		// Fill session data
		sessionService.setCurrentUser(user.get());
		sessionService.set(SesssionKey.CURRENT_USER_DATA, userDataService.findByUser(user.get()).get());
		// Group name is irrelevant at this point maybe later fetch the Group object via
		// service
		sessionService.setCurrentGroup(Group.of(userDataService.findByUser(user.get()).get().getLastActiveGroup()));
		sessionService.set(SesssionKey.LOGIN_TIME, new Date());
		sessionService.set(SesssionKey.LAST_LOGIN_TIME, userData.get().getLastLogin());
		return new RedirectView("/fnet#!/main");
	}

	/**
	 * Logout from the system.
	 * 
	 * @return Login view name
	 */
	@GetMapping("/logout")
	protected String logout() {
		String userName = "no session";
		if (sessionService.isSessionActive()) {
			userName = sessionService.getCurrentUser().getUserName();
		}
		FenceUtil.logout();
		LOG.info("{} logged out", userName);
		return "/login";
	}

	private String createToken() throws NoSuchAlgorithmException {
		// TOO: Need to check the time stamp of the token
		String token = (String) FenceUtil.getSession().getAttribute("token");
		if (token == null) {
			token = new String(HashUtil.generateShortToken());
			FenceUtil.getSession().setAttribute("token", token);
		}
		return token;
	}
}
