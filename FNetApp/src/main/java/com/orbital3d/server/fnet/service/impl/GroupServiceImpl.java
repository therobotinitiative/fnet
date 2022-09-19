package com.orbital3d.server.fnet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.GroupRepository;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.MappingService;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MappingService mappingService;

	@Override
	public GroupRepository getRepository() {
		return groupRepository;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public Group create(String name, boolean createRoot) {
		return getRepository().save(Group.of(name));
	}

	@Override
	public Group create(String name) {
		return create(name, true);
	}

	@Override
	public Optional<Group> findByName(String groupName) {
		return Optional.ofNullable(groupRepository.findByName(groupName));
	}

	@Override
	public boolean isUserInGroup(User user, Group group) {
		return mappingService.userInGroup(user, group);
	}

	@Override
	public List<Group> getByUser(User user) {
		List<Group> groups = new ArrayList<>();
		mappingService.findByUser(user)
				.forEach(userGroupMapping -> groups.add(groupRepository.findByGroupId(userGroupMapping.getGroupId())));
		return groups;
	}

}
