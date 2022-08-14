package com.orbital3d.server.fnet.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.PasswordService;
import com.orbital3d.web.security.weblectricfence.util.HashUtil;

@Service
public class PasswordServiceImpl implements PasswordService {

	@Override
	public byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
		return HashUtil.secureHash(ArrayUtils.addAll(password.getBytes(), salt));
	}

	@Override
	public boolean verifyPassword(User user, String password) {
		try {
			return Arrays.equals(hashPassword(password, user.getSalt()), user.getPassword());
		} catch (NoSuchAlgorithmException e) {
			// Fail silently
		}
		return false;
	}

}
