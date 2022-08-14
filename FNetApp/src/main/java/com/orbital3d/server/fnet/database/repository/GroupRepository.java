package com.orbital3d.server.fnet.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.fnet.database.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Long>
{
	Group findByGroupId(Long groupId);

	Group findByName(String name);

	Iterable<Group> findAllByOrderByGroupId();
}
