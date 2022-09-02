package com.orbital3d.server.fnet.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.PermissionEntity;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.PermissionRepository;
import com.orbital3d.server.fnet.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository prermissionRepository;

	@Override
	@Transactional(TxType.REQUIRED)
	public void add(PermissionEntity permission) {
		prermissionRepository.save(permission);
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void remove(PermissionEntity permission) {
		prermissionRepository.delete(permission);
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void removeAll(User user) {
		prermissionRepository.deleteAll(prermissionRepository.findByUserId(user.getUserId()));
	}

	@Override
	public Iterable<PermissionEntity> forUser(User user) {
		return prermissionRepository.findByUserId(user.getUserId());
	}

	@Override
	public void addAll(Iterable<PermissionEntity> permissions) {
		prermissionRepository.saveAll(permissions);
	}

}
