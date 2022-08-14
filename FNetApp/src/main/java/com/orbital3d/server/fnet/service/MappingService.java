package com.orbital3d.server.fnet.service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserGroupMapping;
import com.orbital3d.server.fnet.database.repository.UserGroupMappingRepository;

public interface MappingService extends CrudService<UserGroupMapping, UserGroupMappingRepository> {
	void addUser(User user, Group groupEntity);
}
