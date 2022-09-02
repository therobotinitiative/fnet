package com.orbital3d.server.fnet.service.impl;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public boolean userInGroup(User user, Group group) {
		return getRepository().userInGroup(user.getUserId(), group.getGroupId());
	}

	@Override
	public List<UserGroupMapping> findByUser(User user) {
		return userGroupMappingRepository.findAllGroupIdByUserId(user.getUserId());
	}

	@Override
	public void deleteByUser(User user) {
		userGroupMappingRepository.deleteByUserId(user.getUserId());
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void addUser(User user, List<Group> groupList) {
		List<UserGroupMapping> allGroups = new ArrayList<>();
		groupList.forEach(group -> allGroups.add(UserGroupMapping.of(user.getUserId(), group.getGroupId())));
		userGroupMappingRepository.saveAll(allGroups);
	}

}
