package com.orbital3d.server.fnet.service;

import java.util.Optional;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.database.repository.UserDataRepository;

public interface UserDataService extends CrudService<UserData, UserDataRepository> {
	default Optional<UserData> findByUser(User user) {
		return getRepository().findByUserId(user.getUserId());
	}

	void updateLastLogin(User user);
}
