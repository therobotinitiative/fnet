package com.orbital3d.server.fnet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.repository.UserRepository;
import com.orbital3d.server.fnet.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

}
