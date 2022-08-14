package com.orbital3d.server.fnet.controller.administrative;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.database.entity.UserGroupMapping;
import com.orbital3d.server.fnet.database.repository.UserGroupMappingRepository;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.PasswordService;
import com.orbital3d.server.fnet.service.UserDataService;
import com.orbital3d.server.fnet.service.UserService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;
import com.orbital3d.web.security.weblectricfence.util.HashUtil;

@RestController
@RequestMapping("/fnet/admin/user")
@ResponseBody
public class AdminUser {
	@Autowired
	private UserService userService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private UserGroupMappingRepository userGroupMappingRepository;

	@Autowired
	private PasswordService passwordService;

	// Variables accessed by framework
	@SuppressWarnings("unused")
	private static final class UserDTO {
		private String userName;
		private Long userId;

		private UserDTO(String userName, Long userId) {
			this.userName = userName;
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public Long getUserId() {
			return userId;
		}

		private void setUserName(String userName) {
			this.userName = userName;
		}

		private void setUserId(Long userId) {
			this.userId = userId;
		}

		public static UserDTO of(String userName, Long userId) {
			return new UserDTO(userName, userId);
		}
	}

	// Variabes accessed by framework
	@SuppressWarnings("unused")
	private static final class CreateUserDTO {
		private String username;
		private Long groupId;

		private CreateUserDTO() {
			// Default
		}

		public String getUsername() {
			return username;
		}

		public Long getGroupId() {
			return groupId;
		}

		private void setUsername(String username) {
			this.username = username;
		}

		private void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

	}

	/**
	 * @return List of all users.
	 */
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermission(FnetPermissions.Administrator.User.LIST)
	protected Set<UserDTO> users() {
		Set<UserDTO> userDTO = new HashSet<>();
		userService.findAll().forEach(user -> {
			userDTO.add(UserDTO.of(user.getUserName(), user.getUserId()));
		});
		return userDTO;
	}

	/**
	 * @param userDto UserDTO containing the information to create new user
	 * @return UserDTO of the newly created user
	 * @throws NoSuchAlgorithmException If the hashing algorithm used for password
	 *                                  is not available
	 */
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@RequiresPermission(FnetPermissions.Administrator.User.CREATE)
	protected UserDTO addUser(@RequestBody CreateUserDTO userDto) throws NoSuchAlgorithmException {
		User user = User.of(null, userDto.getUsername(), new byte[] { 0x00 }, new byte[] { 0x00 });
		// Create user
		user = userService.save(user);
		// Map to group
		userGroupMappingRepository.save(UserGroupMapping.of(user.getUserId(), userDto.groupId));
		// Create user data
		UserData ud = UserData.of(user.getUserId(), "", "", "", null, "", userDto.getGroupId(), new Date(), null);
		userDataService.save(ud);
		return UserDTO.of(userDto.getUsername(), user.getUserId());
	}

	@PutMapping(value = "/password/{userid}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@RequiresPermission(FnetPermissions.Administrator.User.PASSWORD)
	protected Map<String, String> changePassword(@PathVariable(name = "userid") Long userId,
			@PathVariable(name = "password", required = false) String password) throws NoSuchAlgorithmException {
		if (StringUtils.isEmpty(password)) {
			password = RandomStringUtils.randomAlphanumeric(8);
		}
		Optional<User> u = userService.findById(userId);
		if (u.isPresent()) {
			byte[] salt = HashUtil.generateToken();
			byte[] hashed = passwordService.hashPassword(password, salt);
			u.get().setPassword(hashed);
			u.get().setSalt(salt);
			userService.save(u.get());
		}
		Map<String, String> savedPassword = new HashMap<>();
		savedPassword.put("password", password);
		return savedPassword;
	}

	/**
	 * @param userId User ID to delete
	 */
	@DeleteMapping("/{userId}")
	@Transactional
	@RequiresPermission(FnetPermissions.Administrator.User.DELETE)
	protected void deleteUser(@PathVariable Long userId) {
		userService.delete(User.of(userId, null, null, null));
	}

	/**
	 * @param userId User ID to search for
	 * @return List of groups the user is in
	 */
	@GetMapping(value = "/groups/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermission(FnetPermissions.Administrator.User.GROUPS)
	protected Set<Long> getUserGroups(@PathVariable Long userId) {
		Set<Long> groups = new HashSet<>();
		userGroupMappingRepository.findByUserId(userId).forEach(userGroupMapping -> {
			groups.add(userGroupMapping.getGroupId());
		});
		return groups;
	}

	/**
	 * @param userId User ID of whose groups will be updated
	 * @param groups List of groups
	 */
	@PutMapping("/groups/{userId}")
	@Transactional
	@RequiresPermission(FnetPermissions.Administrator.User.UPDATE_GROUPS)
	protected void updateUserGroups(@PathVariable Long userId, @RequestBody Set<Long> groups) {
		userGroupMappingRepository.deleteByUserId(userId);
		Set<UserGroupMapping> userGrouoMappings = new HashSet<>();
		groups.forEach(groupOd -> {
			userGrouoMappings.add(UserGroupMapping.of(userId, groupOd));
		});
		userGroupMappingRepository.saveAll(userGrouoMappings);
	}
}
