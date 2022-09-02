package com.orbital3d.server.fnet.controller.administrative;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.PermissionEntity;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.PermissionService;
import com.orbital3d.server.fnet.service.UserService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

/**
 * Administrative controller for {@link Permission} related operations.
 * 
 * @author msiren
 *
 */
@RestController
@RequestMapping(value = "/fnet/admin/permission")
public class AdmimPermission {
	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * @return List of all available permissions
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermission(FnetPermissions.Administrator.Permission.LIST)
	protected Set<String> getAllPermissions() throws IllegalArgumentException, IllegalAccessException {
		return FnetPermissions.allPermissions();
	}

	/**
	 * @param permissions New permissions
	 * @param userId      User ID whose permissions are updated
	 */
	@PutMapping("/{userId}")
	@Transactional
	@RequiresPermission(FnetPermissions.Administrator.User.UPDATE_PERMISSIONS)
	protected void updatePermissions(@RequestBody Set<String> permissions, @PathVariable Long userId) {
		Set<PermissionEntity> newPermissions = new HashSet<>();
		permissionService.removeAll(userService.getById(userId).get());
		permissions.forEach(permission -> {
			newPermissions.add(PermissionEntity.of(userId, permission));
		});
		permissionService.addAll(newPermissions);
	}

	/**
	 * @param userId User ID whose permissions to get
	 * @return Llist of users permissions
	 */
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermission(FnetPermissions.Administrator.User.PERMISSIONS)
	protected Iterable<String> getUserPermissions(@PathVariable Long userId) {
		Set<String> permssions = new HashSet<>();
		permissionService.forUser(userService.getById(userId).get()).forEach(permission -> {
			permssions.add(permission.getPermission());
		});
		return permssions;
	}

}
