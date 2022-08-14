package com.orbital3d.server.fnet.service;

import java.util.List;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.GroupRepository;

public interface GroupService extends CrudService<Group, GroupRepository> {
	default List<Group> getByUser(User user) {
		return (List<Group>) getRepository().findAll();
	}

	Group create(String name);

	Group create(String name, boolean createRoot);
}
