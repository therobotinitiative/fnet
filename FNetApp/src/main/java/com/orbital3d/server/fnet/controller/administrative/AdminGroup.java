package com.orbital3d.server.fnet.controller.administrative;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

@RestController
@RequestMapping("/fnet/admin/group")
public class AdminGroup {
	@Autowired
	private GroupService groupService;

	/**
	 * @return List of all available groups
	 */
	@GetMapping("/all")
	@RequiresPermission(FnetPermissions.Administrator.GroupOperation.LIST)
	Iterable<Group> listAllGroups() {
		return groupService.findAll();
	}

	/**
	 * @param grooupName Group name to create
	 * @return Newly create group
	 */
	@PostMapping("/create/{groupName}")
	@RequiresPermission(FnetPermissions.Administrator.GroupOperation.CREATE)
	@Transactional
	protected Group createGroup(@PathVariable String grooupName) {
		return groupService.save(Group.of(null, grooupName));
	}
}
