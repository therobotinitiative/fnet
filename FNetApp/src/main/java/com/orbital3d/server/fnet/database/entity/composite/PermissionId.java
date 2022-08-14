package com.orbital3d.server.fnet.database.entity.composite;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PermissionId implements Serializable
{
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 2603443632543332769L;

	@SuppressWarnings("unused")
	private Long userId;

	@SuppressWarnings("unused")
	private String permission;

	public PermissionId()
	{
		// For JPA
	}

	public PermissionId(Long userId, String permission)
	{
		this.userId = userId;
		this.permission = permission;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(67, 790, this);
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(obj, this, false);
	}
}
