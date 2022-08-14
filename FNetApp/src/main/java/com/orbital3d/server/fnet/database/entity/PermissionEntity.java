package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.orbital3d.server.fnet.database.entity.composite.PermissionId;

@Entity
@Table(name = "permission")
@IdClass(PermissionId.class)
public class PermissionEntity
{
	@Id
	private Long userId;

	@Id
	private String permission;

	private PermissionEntity()
	{
		// Default for JPA
	}

	private PermissionEntity(Long userId, String permission)
	{
		this.userId = userId;
		this.permission = permission;
	}

	public Long getUserId()
	{
		return userId;
	}

	public String getPermission()
	{
		return permission;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(137, 13, this);
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(obj, this, false);
	}

	/**
	 * Static factory method.
	 * 
	 * @param userId     User id
	 * @param permission Permission string
	 * @return New {@link PermissionEntity} instance
	 */
	public static PermissionEntity of(final Long userId, final String permission)
	{
		return new PermissionEntity(userId, permission);
	}
}
