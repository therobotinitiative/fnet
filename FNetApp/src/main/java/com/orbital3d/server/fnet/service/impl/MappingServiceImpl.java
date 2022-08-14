package com.orbital3d.server.fnet.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserGroupMapping;
import com.orbital3d.server.fnet.database.repository.UserGroupMappingRepository;
import com.orbital3d.server.fnet.service.MappingService;

@Service
public class MappingServiceImpl implements MappingService {

	@Autowired
	private UserGroupMappingRepository userGroupMappingRepository;

	@Override
	public UserGroupMappingRepository getRepository() {
		return userGroupMappingRepository;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void addUser(User user, Group groupEntity) {
		getRepository().save(UserGroupMapping.of(user.getUserId(), groupEntity.getGroupId()));
	}

}
