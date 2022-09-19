package com.orbital3d.server.fnet.service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.item.SesssionKey;

public interface SessionService {
	Object get(SesssionKey key);

	void set(SesssionKey key, Object value);

	User getCurrentUser();

	Group getCurrentGroup();

	void setCurrentUser(User user);

	void setCurrentGroup(Group group);

	boolean isSessionActive();
}
