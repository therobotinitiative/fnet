package com.orbital3d.server.fnet.service;

import java.security.NoSuchAlgorithmException;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.VFSEntity;
import com.orbital3d.server.fnet.database.repository.VirtualFileSystemRepository;

/**
 * Service for virtual file system operations.
 * 
 * @author msiren
 *
 */
public interface VirtualFileService extends CrudService<VFSEntity, VirtualFileSystemRepository> {
	/**
	 * @param originalName Original file name
	 * @param parentId     Parent {@link Item}
	 * @return Generated {@link VFSEntity}
	 * @throws NoSuchAlgorithmException
	 */
	VFSEntity generate(String originalName, Long parentId) throws NoSuchAlgorithmException;

	VFSEntity getVrtualFile(Long vfsId);
}
