package com.orbital3d.server.fnet.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.UserRepository;
import com.orbital3d.server.fnet.service.PasswordService;
import com.orbital3d.server.fnet.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordService passwordService;

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

	@Override
	@Transactional
	public User createUser(String userName, String password) {
		if (StringUtils.isBlank(userName)) {
			throw new IllegalArgumentException("User name cannot be empty");
		}
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		// Prepare password and save user
		Pair<String, String> passwordPair = passwordService.hash(password);
		User user = userRepository.save(User.of(userName, passwordPair.getLeft(), passwordPair.getRight()));
		return user;
	}

	@Override
	public User changePassword(User user, String password) {
		Pair<String, String> passwordPair = passwordService.hash(password);
		return userRepository
				.save(User.of(user.getUserId(), user.getUserName(), passwordPair.getLeft(), passwordPair.getRight()));
	}

}
