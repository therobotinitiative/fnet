package com.orbital3d.server.fnet.controller.administrative;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.UserDataService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

/**
 * Administrative controller for {@link UserData} related operations.
 * 
 * @author msiren
 *
 */
@RestController
@RequestMapping("/fnet/admin/userdata")
public class AdminUserData {
	@Autowired
	private UserDataService userDataService;

	/**
	 * DTO class user data.
	 * 
	 * @author msiren
	 *
	 */
	private static final class UserDataDTO {
		private Long userId;
		private String firstName;
		private String lastName;
		private String email;

		private UserDataDTO() {
			// Default
		}

		public Long getUserId() {
			return userId;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getEmail() {
			return email;
		}

	}

	/**
	 * @param userId
	 * @return User data for the given user id
	 */
	@GetMapping("/{userId}")
	@RequiresPermission(FnetPermissions.Administrator.Userdata.GET)
	protected UserData getUserData(@PathVariable Long userId) {
		Optional<UserData> userData = userDataService.getById(userId);
		if (userData.isPresent()) {
			return userData.get();
		} else {
			return UserData.of(userId);
		}
	}

	/**
	 * @param userDataDTO User data DTO containing the new user data to update
	 */
	@PutMapping("/")
	@RequiresPermission(FnetPermissions.Administrator.Userdata.UPDATE)
	@Transactional
	protected void updateUserData(@RequestBody UserDataDTO userDataDTO) {
		Optional<UserData> uerData = userDataService.getById(userDataDTO.getUserId());
		if (uerData.isPresent()) {
			uerData.get().setFirstName(userDataDTO.getFirstName());
			uerData.get().setLastName(userDataDTO.getLastName());
			uerData.get().setEmail(userDataDTO.getEmail());
			userDataService.save(uerData.get());
		}
	}
}
