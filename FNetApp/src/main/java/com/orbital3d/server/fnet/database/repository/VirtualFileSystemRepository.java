package com.orbital3d.server.fnet.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.fnet.database.entity.VFSEntity;

public interface VirtualFileSystemRepository extends CrudRepository<VFSEntity, Long>
{
	Optional<VFSEntity> findByParentId(Long parentId);
}
