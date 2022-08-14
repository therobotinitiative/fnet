package com.orbital3d.server.fnet.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.PermissionService;
import com.orbital3d.web.security.weblectricfence.authorization.AuthorizationWorker.Authorizer;
import com.orbital3d.web.security.weblectricfence.exception.AuthorizationException;
import com.orbital3d.web.security.weblectricfence.type.Permission;
import com.orbital3d.web.security.weblectricfence.type.WebLectricSubject;

@Component
public class FnetAuthorizer implements Authorizer {
	@Autowired
	private PermissionService permissionService;

	@Override
	public void authorize(WebLectricSubject su, Permission p) throws AuthorizationException {
		Iterator<Permission> permissionIterator = su.getPermissions().iterator();
		while (permissionIterator.hasNext()) {
			if (permissionIterator.next().isPermitted(p)) {
				return;
			}
		}
		throw new AuthorizationException();
	}

	@Override
	public void gatherPermissions(WebLectricSubject su) {
		Set<Permission> pset = new HashSet<>();
		permissionService.forUser(((User) su.getIdentity())).forEach(perm -> pset.add(Permission.of(perm.getPermission())));
		su.setPermissions(pset);
	}

}
