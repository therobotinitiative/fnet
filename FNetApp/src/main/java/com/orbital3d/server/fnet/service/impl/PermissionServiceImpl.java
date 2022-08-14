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
	private PermissionRepository pr;

	@Override
	@Transactional(TxType.REQUIRED)
	public void add(PermissionEntity permission) {
		pr.save(permission);
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void remove(PermissionEntity permission) {
		pr.delete(permission);
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void removeAll(User user) {
		pr.deleteAll(pr.findByUserId(user.getUserId()));
	}

	@Override
	public Iterable<PermissionEntity> forUser(User user) {
		return pr.findByUserId(user.getUserId());
	}

	@Override
	public void addAll(Iterable<PermissionEntity> permissions) {
		pr.saveAll(permissions);
	}

}
