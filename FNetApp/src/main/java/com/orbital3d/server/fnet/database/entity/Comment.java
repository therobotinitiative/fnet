package com.orbital3d.server.fnet.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "comment")
public class Comment
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@NotNull
	private Long itemId;

	@NotNull
	private Long groupId;

	@NotNull
	private Long userId;

	@NotNull
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	protected Comment()
	{
		// For JPA
	}

	private Comment(Long commentId, @NotNull Long itemId, @NotNull Long groupId, @NotNull Long userId, @NotNull String comment, Date timestamp)
	{
		super();
		this.commentId = commentId;
		this.itemId = itemId;
		this.groupId = groupId;
		this.userId = userId;
		this.comment = comment;
		this.timestamp = timestamp;
	}

	public Long getCommentId()
	{
		return commentId;
	}

	public void setId(Long commentId)
	{
		this.commentId = commentId;
	}

	public Long getItemId()
	{
		return itemId;
	}

	public void setItemId(Long itemId)
	{
		this.itemId = itemId;
	}

	public Long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(7, 9, this);
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
	 * @param commentId
	 * @param itemId
	 * @param groupId
	 * @param userId
	 * @param comment
	 * @param timestamp
	 * @return
	 */
	public static Comment of(Long commentId, @NotNull Long itemId, @NotNull Long groupId, @NotNull Long userId, @NotNull String comment, Date timestamp)
	{
		return new Comment(commentId, itemId, groupId, userId, comment, timestamp);
	}
}
