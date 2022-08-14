package com.orbital3d.server.fnet.service;

import com.orbital3d.server.fnet.database.entity.PermissionEntity;
import com.orbital3d.server.fnet.database.entity.User;

public interface PermissionService {
	void add(PermissionEntity permission);

	void remove(PermissionEntity permission);

	void removeAll(User user);

	Iterable<PermissionEntity> forUser(User user);

	void addAll(Iterable<PermissionEntity> permissions);
}
