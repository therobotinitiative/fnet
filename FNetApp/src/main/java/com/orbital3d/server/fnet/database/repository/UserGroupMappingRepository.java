package com.orbital3d.server.fnet.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.fnet.database.entity.UserGroupMapping;

public interface UserGroupMappingRepository extends CrudRepository<UserGroupMapping, Long> {
	List<UserGroupMapping> findByUserId(Long userId);

	@Transactional
	void deleteByUserId(Long userId);

	UserGroupMapping userIdAndGroupId(Long userId, Long groupId);
}
