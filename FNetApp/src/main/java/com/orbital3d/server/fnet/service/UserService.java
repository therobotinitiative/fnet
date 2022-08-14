package com.orbital3d.server.fnet.service;

import java.util.Optional;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.UserRepository;

public interface UserService extends CrudService<User, UserRepository> {
	default Optional<User> findUserByName(String userName) {
		return getRepository().findByUserName(userName);
	}
}
