package com.orbital3d.server.fnet.database.entity.composite;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserGroupId implements Serializable
{
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -408006539880136129L;

	@SuppressWarnings("unused")
	private Long userId;

	@SuppressWarnings("unused")
	private Long groupId;

	protected UserGroupId()
	{
		// For JPA
	}

	public UserGroupId(Long userId, Long groupId)
	{
		this.userId = userId;
		this.groupId = groupId;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(27, 125, this);
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
}
