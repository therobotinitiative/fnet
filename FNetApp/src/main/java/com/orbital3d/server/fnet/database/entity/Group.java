package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;

@Entity
@Table(name = "user_group")
public class Group implements ServiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId;

	private String name;

	protected Group() {
		// For JPA
	}

	private Group(Long groupId, String name) {
		super();
		this.groupId = groupId;
		this.name = name;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(31, 27, this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static Group of(Long groupId, String name) {
		return new Group(groupId, name);
	}

	public static Group of(Long groupId) {
		return new Group(groupId, null);
	}

	public static Group of(String name) {
		return new Group(null, name);
	}
}
