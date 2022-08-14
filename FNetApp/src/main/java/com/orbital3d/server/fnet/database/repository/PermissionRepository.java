package com.orbital3d.server.fnet.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.fnet.database.entity.PermissionEntity;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long>
{
	Iterable<PermissionEntity> findByUserId(Long userId);

	void deleteByUserId(Long userId);
}
