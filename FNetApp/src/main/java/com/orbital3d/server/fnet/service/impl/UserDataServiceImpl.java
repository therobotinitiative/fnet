package com.orbital3d.server.fnet.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.entity.UserData;
import com.orbital3d.server.fnet.database.repository.UserDataRepository;
import com.orbital3d.server.fnet.service.UserDataService;

@Service
public class UserDataServiceImpl implements UserDataService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Override
	public UserDataRepository getRepository() {
		return userDataRepository;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void updateLastLogin(User user) {
		Optional<UserData> ud = userDataRepository.findById(user.getUserId());
		if (!ud.isEmpty()) {
			userDataRepository.updateLastLogin(user.getUserId(), "na");
		}
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void updateActiveGroup(User user, Long groupId) {
		userDataRepository.updateLastActiveGroup(user.getUserId(), groupId);
	}

}
