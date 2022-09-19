package com.orbital3d.server.fnet.controller.administrative;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

/**
 * Administrative controller for {@link Group} related operations.
 * 
 * @author msiren
 *
 */
@RestController
@RequestMapping("/fnet/admin/group")
public class AdminGroup {
	@Autowired
	private GroupService groupService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SessionService sessionService;

	/**
	 * DTO class for group.
	 * 
	 * @author msiren
	 *
	 */
	private static class GroupDTO {
		private String groupName;

		public String getGroupName() {
			return groupName;
		}
	}

	/**
	 * @return List of all available groups
	 */
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermission(FnetPermissions.Administrator.GroupOperation.LIST)
	Iterable<Group> listAllGroups() {
		return groupService.findAll();
	}

	/**
	 * @param grooupName Group name to create
	 * @return Newly create group
	 */
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermission(FnetPermissions.Administrator.GroupOperation.CREATE)
	@Transactional
	protected Group createGroup(@RequestBody GroupDTO groupDTO) {
		if (StringUtils.isAllEmpty(groupDTO.getGroupName())) {
			throw new IllegalArgumentException("Group name mst noy be empty");
		}
		Group newGroup = groupService.save(Group.of(groupDTO.getGroupName()));
		itemService.createRoot(newGroup, sessionService.getCurrentUser().getUserId());
		return newGroup;
	}

	/**
	 * @param groupId Group id to delete
	 */
	@DeleteMapping("/{groupId}")
	@RequiresPermission(FnetPermissions.Administrator.GroupOperation.DELETE)
	@Transactional
	protected void deleteGroup(@PathVariable Long groupId) {
		groupService.delete(Group.of(groupId));
	}
}
