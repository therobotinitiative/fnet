package com.orbital3d.server.fnet.security;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.PasswordService;
import com.orbital3d.server.fnet.service.UserService;
import com.orbital3d.web.security.weblectricfence.authentication.AuthenticationToken;
import com.orbital3d.web.security.weblectricfence.authentication.AuthenticationWorker.Authenticator;
import com.orbital3d.web.security.weblectricfence.authentication.token.UsernamePasswordToken;
import com.orbital3d.web.security.weblectricfence.exception.AuthenticationException;
import com.orbital3d.web.security.weblectricfence.type.UserIdentity;

@Component
public class FNetAuthenticator implements Authenticator {
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordService passwordService;

	@Override
	public UserIdentity authenticate(AuthenticationToken token) throws AuthenticationException, LoginException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		Optional<User> user = userService.findUserByName(usernamePasswordToken.getUsername());
		if (user.isPresent()) {
//			byte[] password = ArrayUtils.addAll(usernamePasswordToken.getPassword().getBytes(), user.get().getSalt());
			if (passwordService.verifyPassword(user.get(), usernamePasswordToken.getPassword())) {
				return user.get();
			}
		}
		throw new LoginException();
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token.getClass().isAssignableFrom(UsernamePasswordToken.class);
	}

}
