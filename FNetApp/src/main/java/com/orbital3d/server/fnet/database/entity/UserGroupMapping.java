package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.database.entity.composite.UserGroupId;
import com.orbital3d.server.fnet.service.item.ServiceItem;

@Entity
@Table(name = "user_group_mapping")
@IdClass(UserGroupId.class)
public class UserGroupMapping implements ServiceItem {
	@Id
	private Long userId;

	@Id
	private Long groupId;

	protected UserGroupMapping() {
		// For JPA
	}

	private UserGroupMapping(Long userId, Long groupId) {
		super();
		this.userId = userId;
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(3, 7, this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * Static factory method.
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public static UserGroupMapping of(Long userId, Long groupId) {
		return new UserGroupMapping(userId, groupId);
	}
}
