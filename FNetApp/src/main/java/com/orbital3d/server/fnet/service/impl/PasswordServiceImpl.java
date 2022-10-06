package com.orbital3d.server.fnet.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.PasswordService;
import com.orbital3d.web.security.weblectricfence.util.HashUtil;

@Service
public class PasswordServiceImpl implements PasswordService {

	private static final Logger LOG = LoggerFactory.getLogger(PasswordServiceImpl.class);

	@Override
	public byte[] hash(byte[] password, byte[] salt) throws NoSuchAlgorithmException {
		return HashUtil.secureHash(ArrayUtils.addAll(password, salt));
	}

	@Override
	public boolean verifyPassword(User user, String password) {
		if (user == null) {
			throw new IllegalArgumentException("User must not be null");
		}
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Passwrd cannt be empty");
		}
		try {
			byte[] passwordUTF8Bytes = password.getBytes(StandardCharsets.UTF_8);
			byte[] hashedPasswordBytes = Base64.getDecoder().decode(user.getPassword());
			byte[] saltBytes = Base64.getDecoder().decode(user.getSalt());
			return Arrays.equals(hash(passwordUTF8Bytes, saltBytes), hashedPasswordBytes);
		} catch (NoSuchAlgorithmException e) {
			// Fail silently
		}
		return false;
	}

	@Override
	public Pair<String, String> hash(String password) {
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Passwrd cannt be empty");
		}
		try {
			byte[] salt = HashUtil.fillSecure(new byte[32]);
			byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
			byte[] hashedPassword = HashUtil.secureHash(ArrayUtils.addAll(passwordBytes, salt));
			return Pair.of(Base64.getEncoder().encodeToString(hashedPassword),
					Base64.getEncoder().encodeToString(salt));
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Fix your JDK", e);
		}
		return null;
	}

}
