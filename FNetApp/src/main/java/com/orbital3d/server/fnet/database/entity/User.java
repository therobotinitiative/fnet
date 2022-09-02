package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;
import com.orbital3d.web.security.weblectricfence.type.UserIdentity;

@Entity
@Table(name = "user")
public class User implements ServiceItem, UserIdentity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotNull
	private String userName;

	@NotNull
	private byte[] password;

	@NotNull
	private byte[] salt;

	protected User() {
		// For JPA
	}

	private User(Long userId, String userName, byte[] password, byte[] salt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(197, 37, this, false);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return "[" + this.userId + "](" + this.userName + ")";
	}

	public static User of(Long userId, String userName, byte[] password, byte[] salt) {
		return new User(userId, userName, password, salt);
	}

	public static User of(Long userId) {
		return new User(userId, null, null, null);
	}
}
