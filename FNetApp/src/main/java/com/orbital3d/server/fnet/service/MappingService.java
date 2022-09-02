package com.orbital3d.server.fnet.service;

import java.util.List;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserGroupMapping;
import com.orbital3d.server.fnet.database.repository.UserGroupMappingRepository;

public interface MappingService extends CrudService<UserGroupMapping, UserGroupMappingRepository> {
	void addUser(User user, Group groupEntity);

	void addUser(User user, List<Group> groupList);

	boolean userInGroup(User user, Group group);

	List<UserGroupMapping> findByUser(User user);

	void deleteByUser(User user);
}
