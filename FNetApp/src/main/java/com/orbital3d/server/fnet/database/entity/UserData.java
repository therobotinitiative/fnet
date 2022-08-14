package com.orbital3d.server.fnet.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;

@Entity
@Table(name = "user_data")
public class UserData implements ServiceItem {
	@Id
	private Long userId;

	private String firstName;

	private String lastName;

	private String email;

	@Column(name = "last_login", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	private String lastLoginIp;

	private Long lastActiveGroup;

	@Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name = "password_changed", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date passwordChanged;

	protected UserData() {
		// For JPA
	}

	private UserData(Long userId, String firstName, String lastName, String email, Date lastLogin,
			String lastLoginIp, Long lastActiveGroup, Date created, Date passwordChanged) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.lastLogin = lastLogin;
		this.lastLoginIp = lastLoginIp;
		this.lastActiveGroup = lastActiveGroup;
		this.created = created;
		this.passwordChanged = passwordChanged;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fistName) {
		this.firstName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Long getLastActiveGroup() {
		return lastActiveGroup;
	}

	public void setLastActiveGroup(Long lastActiveGroup) {
		this.lastActiveGroup = lastActiveGroup;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getPasswordChaned() {
		return passwordChanged;
	}

	public void setPasswordChaned(Date passwordChaned) {
		this.passwordChanged = passwordChaned;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(93, 59, this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	public static UserData of(Long userId, String firstName, String lastName, String email, Date lastLogin,
			String lastLoginIp, Long lastActiveGroup, Date created, Date passwordChanged) {
		return new UserData(userId, firstName, lastName, email, lastLogin, lastLoginIp, lastActiveGroup, created,
				passwordChanged);
	}
}
