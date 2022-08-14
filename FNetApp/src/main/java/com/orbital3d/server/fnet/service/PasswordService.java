package com.orbital3d.server.fnet.service;

import java.security.NoSuchAlgorithmException;

import com.orbital3d.server.fnet.database.entity.User;

public interface PasswordService {
	byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException;

	boolean verifyPassword(User user, String password);
}
