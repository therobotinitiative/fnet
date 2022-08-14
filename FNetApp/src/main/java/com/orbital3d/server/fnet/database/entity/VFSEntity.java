package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "vfs")
public class VFSEntity
{
	@Id
	@NotNull
	private Long parentId;

	@NotNull
	private String virtualName;

	@NotNull
	private String originalName;

	protected VFSEntity()
	{
		// For JPA
	}

	private VFSEntity(@NotNull Long parentId, @NotNull String virtualName, @NotNull String originalName)
	{
		this.parentId = parentId;
		this.virtualName = virtualName;
		this.originalName = originalName;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public String getVirtualName()
	{
		return virtualName;
	}

	public void setVirtualName(String virtualName)
	{
		this.virtualName = virtualName;
	}

	public String getOriginalName()
	{
		return originalName;
	}

	public void setOriginalName(String originalName)
	{
		this.originalName = originalName;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(77, 43, this);
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * Static factory method.
	 * 
	 * @param parentId
	 * @param virtualName
	 * @param originalName
	 * @return
	 */
	public static VFSEntity of(@NotNull Long parentId, @NotNull String virtualName, @NotNull String originalName)
	{
		return new VFSEntity(parentId, virtualName, originalName);
	}
}
