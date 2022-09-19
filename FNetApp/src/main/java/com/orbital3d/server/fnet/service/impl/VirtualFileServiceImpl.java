package com.orbital3d.server.fnet.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.VFSEntity;
import com.orbital3d.server.fnet.database.repository.VirtualFileSystemRepository;
import com.orbital3d.server.fnet.service.VirtualFileService;

@Service
public class VirtualFileServiceImpl implements VirtualFileService {
	@Autowired
	private VirtualFileSystemRepository virtualFileSystemRepository;

	@Override
	public VFSEntity generate(String originalName, Long parentId) throws NoSuchAlgorithmException {
		String genname = originalName + SecureRandom.getInstance("SHA1PRNG").nextInt();
		return VFSEntity.of(parentId, Base64.getEncoder().encodeToString(genname.getBytes()), originalName);
	}

	@Override
	public VFSEntity getVrtualFile(Long vfsId) {
		return virtualFileSystemRepository.findByParentId(vfsId).get();
	}

	@Override
	public VirtualFileSystemRepository getRepository() {
		return virtualFileSystemRepository;
	}

}
