package com.orbital3d.server.fnet.service;

import java.util.List;
import java.util.Optional;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.GroupRepository;

public interface GroupService extends CrudService<Group, GroupRepository> {
	List<Group> getByUser(User user);

	Group create(String name);

	Group create(String name, boolean createRoot);

	Optional<Group> findByName(String groupName);

	boolean isUserInGroup(User user, Group group);
}
