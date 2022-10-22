package com.orbital3d.server.fnet.service;

import java.util.Optional;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.database.repository.UserDataRepository;

/**
 * Service for user data related operations.
 * 
 * @author msiren
 *
 */
public interface UserDataService extends CrudService<UserData, UserDataRepository> {
	default Optional<UserData> findByUser(User user) {
		return getRepository().findByUserId(user.getUserId());
	}

	/**
	 * @param user {@link User} which last login time is being updated
	 */
	void updateLastLogin(User user);

	/**
	 * Update the last active group in the user data.
	 * 
	 * @param user    {@link User} which user data is being updated
	 * @param groupId Updated last active group id
	 */
	void updateActiveGroup(User user, Long groupId);
}
