package com.orbital3d.server.fnet.service.impl;

import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.item.SesssionKey;
import com.orbital3d.web.security.weblectricfence.util.FenceUtil;

@Service
public class SessionServiceImpl implements SessionService {

	@Override
	public Object get(SesssionKey key) {
		return FenceUtil.getSession().getAttribute(key.name());
	}

	@Override
	public void set(SesssionKey key, Object value) {
		FenceUtil.getSession().setAttribute(key.name(), value);
	}

	@Override
	public User getCurrentUser() {
		return (User) get(SesssionKey.CURRENT_USER);
	}

	@Override
	public Group getCurrentGroup() {
		return (Group) get(SesssionKey.CURRENT_ACTIVE_GROUP);
	}

	@Override
	public void setCurrentUser(User user) {
		set(SesssionKey.CURRENT_USER, user);
	}

	@Override
	public void setCurrentGroup(Group group) {
		set(SesssionKey.CURRENT_ACTIVE_GROUP, group);
	}

}
