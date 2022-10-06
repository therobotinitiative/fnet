package com.orbital3d.server.fnet.service;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.tuple.Pair;

import com.orbital3d.server.fnet.database.entity.User;

/**
 * Service for password related operations.All things related to password should
 * be performed using this service.
 * 
 * @author msiren
 *
 */
public interface PasswordService {
	/**
	 * Hash the given password with given salt.
	 * 
	 * @param password Password as byte array
	 * @param salt     Salt as byte array
	 * @return Hashed password
	 * @throws NoSuchAlgorithmException
	 */
	byte[] hash(byte[] password, byte[] salt) throws NoSuchAlgorithmException;

	/**
	 * Hash given password with salt that is generated here. Return values are byte
	 * arrays with base64 encoding.
	 * 
	 * @param password
	 * @return {@link Pair} left containing the password, righht containing the salt
	 */
	Pair<String, String> hash(String password);

	/**
	 * @param user     {@link User} to verify
	 * @param password Password to verify against the given {@link User}
	 * @return true is password match; false otherwise
	 */
	boolean verifyPassword(User user, String password);
}
