package com.orbital3d.server.fnet.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.repository.GroupRepository;
import com.orbital3d.server.fnet.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public GroupRepository getRepository() {
		return groupRepository;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public Group create(String name, boolean createRoot) {
		return getRepository().save(Group.of(null, name));
	}

	@Override
	public Group create(String name) {
		return create(name, true);
	}

}
